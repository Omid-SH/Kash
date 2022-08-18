package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.google.gson.Gson;


public class TokenGetter extends Thread {

    private String username;
    private String password;
    private Handler handler;

    public TokenGetter(String username, String password, Handler handler) {
        this.username = username;
        this.password = password;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();

        // Token default lifeTime is 4h,
        // by changing "no" to "yes" you
        // increase it's lifeTime to 30days.

        String url = "https://api.nobitex.ir/auth/login/";
        String json = "{\"username\":\"" + username + "\"," +
            "\"password\":\"" + password + "\"," +
            "\"remember\":\"no\"}";

        String result = Nobitex.post(url, json);

        if (result.contains("err")) {
            Message message = new Message();
            message.what = DataManager.TOKEN_GOT;
            message.obj = null;
            handler.sendMessage(message);
            return;
        }

        TokenRequestAnswer tokenRequestAnswer = new Gson().fromJson(result, TokenRequestAnswer.class);

        Log.v(Nobitex.TAG, result);

        if (tokenRequestAnswer.status.equals("success")) {

            Nobitex.setToken(tokenRequestAnswer.getKey());

            Message message = new Message();
            message.what = DataManager.TOKEN_GOT;
            message.obj = tokenRequestAnswer.getStatus();
            handler.sendMessage(message);

        } else {

            Message message = new Message();
            message.what = DataManager.TOKEN_GOT;
            message.obj = null;
            handler.sendMessage(message);

        }

    }

    static class TokenRequestAnswer {

        private String status;
        private String key;
        private String device;

        // Getter Methods

        public String getStatus() {
            return status;
        }

        public String getKey() {
            return key;
        }

        public String getDevice() {
            return device;
        }

        // Setter Methods

        public void setStatus(String status) {
            this.status = status;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setDevice(String device) {
            this.device = device;
        }
    }

}
