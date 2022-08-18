package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.example.nobintest.nobitex.dataTypes.Order;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class OrderAdder extends Thread {
    private String type;
    private String execution;
    private String srcCurrency;
    private String dstCurrency;
    private String amount;
    private String price;
    private Handler handler;


    public OrderAdder(String type,
                      String execution,
                      String srcCurrency,
                      String dstCurrency,
                      String amount,
                      String price,
                      Handler handler) {

        this.amount = amount;
        this.dstCurrency = dstCurrency;
        this.srcCurrency = srcCurrency;
        this.type = type;
        this.price = price;
        this.execution = execution;
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

        String json = "{\"type\":\"" + type + "\"," +
            "\"execution\":\"" + execution + "\"," +
            "\"srcCurrency\":\"" + srcCurrency + "\"," +
            "\"dstCurrency\":\"" + dstCurrency + "\"," +
            "\"amount\":\"" + amount + "\"," +
            "\"price\":" + price + "}";

        String result = Nobitex.post("https://api.nobitex.ir/market/orders/add", json
            , token);

        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.v(TAG, result);

            if (jsonObject.get("status").equals("failed")) {

                OrderError orderError = new Gson().fromJson(result, OrderError.class);
                Log.v(TAG, orderError.message);

                Message message = new Message();
                message.what = DataManager.ADD_ORDER;
                message.obj = orderError;
                handler.sendMessage(message);

            } else {

                OrderAnswer orderAnswer = new Gson().fromJson(result, OrderAnswer.class);

                Message message = new Message();
                message.what = DataManager.ADD_ORDER;
                message.obj = orderAnswer.getOrder();
                handler.sendMessage(message);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static class OrderError {
        private String status;
        private String code;
        private String message;

        // Getter Methods

        public String getStatus() {
            return status;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        // Setter Methods

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class OrderAnswer {

        private String status;
        private Order order;

        public OrderAnswer(String status, Order order) {
            this.status = status;
            this.order = order;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

    }
}
