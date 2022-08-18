package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class BankCardAdder extends Thread {

    private String number;
    private String bank;
    private Handler handler;

    public BankCardAdder(String number, String bank, Handler handler) {
        this.number = number;
        this.bank = bank;
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

        String result = Nobitex.post("https://api.nobitex.ir/users/cards-add",
            "{\"number\":\"" + number + "\",\"bank\":\"" + bank + "\"}", token);

        String status = "failed";
        try {
            JSONObject response = new JSONObject(result);
            status = (String) response.get("status");
            Log.v(TAG, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Message message = new Message();
        message.what = DataManager.BANK_CARD_ADD;
        message.obj = status;
        handler.sendMessage(message);

    }

}
