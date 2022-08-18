package com.example.nobintest.nobitex.apiThreads;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.example.nobintest.nobitex.dataTypes.OrderStatus;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

public class OrderStatusGetter extends Thread {

    private String id;
    private Handler handler;

    public OrderStatusGetter(String id, Handler handler) {
        this.id = id;
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

        String json = "{\"id\":\"" + id + "\"}";
        String result = Nobitex.post("https://api.nobitex.ir/market/orders/status", json
            , token);

        OrderStatusAnswer orderStatusAnswer = new Gson().fromJson(result, OrderStatusAnswer.class);
        Log.v(TAG, orderStatusAnswer.toString());

        Message message = new Message();
        message.what = DataManager.ORDER_STATUS_GOT;

        if (orderStatusAnswer.status.equals("ok")) {
            message.obj = orderStatusAnswer.getOrder();
        } else {
            message.obj = null;
        }
        handler.sendMessage(message);

    }

    public static class OrderStatusAnswer {
        private String status;
        private OrderStatus order;

        public OrderStatusAnswer(String status, OrderStatus order) {
            this.order = order;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public OrderStatus getOrder() {
            return order;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setOrder(OrderStatus order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return "OrderStatusAnswer{" +
                "status='" + status + '\'' +
                ", order=" + order +
                '}';
        }
    }

}
