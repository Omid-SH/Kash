package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class OrderBookGetter extends Thread {

    private String symbol; // Example : symbol = "BTC"
    private Handler handler;

    public OrderBookGetter(String symbol, Handler handler) {
        this.symbol = symbol;
        this.handler = handler;
    }

    @Override
    public void run() {

        super.run();

        Map<String, String> bidsOrders = new HashMap<>();
        Map<String, String> asksOrders = new HashMap<>();

        Log.v(Nobitex.TAG, "startGettingOrders");

        String result = Nobitex.post("https://api.nobitex.ir/v2/orderbook",
            "{\"symbol\":\"" + symbol + "\"}");

        Log.v(Nobitex.TAG, result);


        JSONObject obj = null;
        String status = "";
        try {

            obj = new JSONObject(result);
            status = (String) obj.get("status");
            JSONArray bidsArr = obj.getJSONArray("bids");
            JSONArray asksArr = obj.getJSONArray("asks");

            for (int i = 0; i < bidsArr.length(); i++) {
                bidsOrders.put(bidsArr.getJSONArray(i).get(0).toString(), bidsArr.getJSONArray(i).get(1).toString());
            }

            for (int i = 0; i < asksArr.length(); i++) {
                asksOrders.put(asksArr.getJSONArray(i).get(0).toString(), asksArr.getJSONArray(i).get(1).toString());
            }

            Log.v(Nobitex.TAG, "OrdersGot");
            Log.v(Nobitex.TAG, bidsOrders.toString());
            Log.v(Nobitex.TAG, asksOrders.toString());

        } catch (JSONException e) {
            Log.v(Nobitex.TAG, e.getMessage());
        }

        Message message = new Message();
        message.what = DataManager.ORDER_BOOK_GOT;

        if (status.equals("ok")) {
            message.obj = new OrderBook(bidsOrders, asksOrders);
        } else {
            message.obj = null;
        }

        handler.sendMessage(message);

    }

    public static class OrderBook {
        Map<String, String> bids;
        Map<String, String> asks;

        /**
         * for(String string: bids.keySet()){
         * --> add to recyclerView dataSet.
         * --> String totalValue = bids.get(string);
         * }
         */

        public OrderBook(Map<String, String> bids, Map<String, String> asks) {
            this.bids = bids;
            this.asks = asks;

        }

        public Map<String, String> getBids() {
            return bids;
        }

        public void setBids(Map<String, String> bids) {
            this.bids = bids;
        }

        public Map<String, String> getAsks() {
            return asks;
        }

        public void setAsks(Map<String, String> asks) {
            this.asks = asks;
        }
    }

}
