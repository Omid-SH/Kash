package com.example.nobintest.nobitex.apiThreads;

import android.util.Log;

import com.example.nobintest.nobitex.Nobitex;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class OrderCanceller extends Thread {

    private String execution;
    private String srcCurrency;
    private String dstCurrency;
    private String hours;

    public OrderCanceller(String execution, String srcCurrency, String dstCurrency, String hours) {
        this.execution = execution;
        this.hours = hours;
        this.srcCurrency = srcCurrency;
        this.dstCurrency = dstCurrency;
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

        String json = "{\"execution\":\"" + execution + "\"," +
            "\"srcCurrency\":\"" + srcCurrency + "\"," +
            "\"dstCurrency\":\"" + dstCurrency + "\"," +
            "\"hours\":" + hours + "}";

        String result = Nobitex.post("https://api.nobitex.ir/market/orders/cancel-old", json
            , token);

        try {
            String status = new JSONObject(result).getString("status");
            Log.v(TAG, result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
