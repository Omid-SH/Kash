package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;


public class BankAccountAdder extends Thread {


    private String number;
    private String shaba;
    private String bank;
    private Handler handler;

    public BankAccountAdder(String number, String shaba, String bank, Handler handler) {
        this.bank = bank;
        this.number = number;
        this.shaba = shaba;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();

        String token = Nobitex.getToken();

        while (token == null) {
            try {
                // wait to acquire Token.
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            token = Nobitex.getToken();
        }

        String json = "{\"number\":\"" + number + "\",\"shaba\":\"" + shaba + "\",\"bank\":\"" + bank + "\"}";
        Log.v(TAG, json);

        String result = Nobitex.post("https://api.nobitex.ir/users/accounts-add",
            json,
            token);

        String status = "failed";
        try {
            JSONObject response = new JSONObject(result);
            status = (String) response.get("status");
            Log.v(TAG, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Message message = new Message();
        message.what = DataManager.BANK_ACCOUNT_ADD;
        message.obj = status;
        handler.sendMessage(message);

    }

}

