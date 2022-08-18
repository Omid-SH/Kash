package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;


public class OrderUpdater extends Thread {

    private String id;
    private String status;
    private Handler handler;

    // status : new , active , canceled.

    public OrderUpdater(String id, String status, Handler handler) {
        this.id = id;
        this.status = status;
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

        String json = "{\"order\":" + id + ",\"status\":\"" + status + "\"}";
        Log.v(TAG, json);

        String result = Nobitex.post("https://api.nobitex.ir/market/orders/update-status", json, token);
        if (result.equals("error Happened While Posting Data To Server.")) {
            Log.v(TAG, "invalid State(or Order) Change Request");

            Message message = new Message();
            message.what = DataManager.UPDATE_ORDER;
            message.obj = "invalid Change Request";
            handler.sendMessage(message);
            return;
        }

        ChangeOrderAnswer changeOrderAnswer = new Gson().fromJson(result, ChangeOrderAnswer.class);

        Message message = new Message();
        message.what = DataManager.UPDATE_ORDER;
        message.obj = changeOrderAnswer.updatedStatus;
        handler.sendMessage(message);

    }

    public static class ChangeOrderAnswer {
        private String status;
        private String updatedStatus;

        ChangeOrderAnswer(String status, String updatedStatus) {
            this.status = status;
            this.updatedStatus = updatedStatus;
        }

        // Getter Methods
        public String getStatus() {
            return status;
        }

        public String getUpdatedStatus() {
            return updatedStatus;
        }

        // Setter Methods
        public void setStatus(String status) {
            this.status = status;
        }

        public void setUpdatedStatus(String updatedStatus) {
            this.updatedStatus = updatedStatus;
        }
    }

}
