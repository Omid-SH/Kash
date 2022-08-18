package com.example.nobintest.nobitex.apiThreads;

import android.util.Log;

import com.example.nobintest.nobitex.Nobitex;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TradeListGetter extends Thread {

    private String symbol;

    public TradeListGetter(String symbol) {
        this.symbol = symbol;
    }

    public void run() {

        super.run();
        Log.v(Nobitex.TAG, "startGettingTrades");
        try {

            String result = Nobitex.post("https://api.nobitex.ir/v2/trades",
                "{\"symbol\":\"" + symbol + "\"}");

            TradeAnswer tradeAnswer = new Gson().fromJson(result, TradeAnswer.class);
            Log.v(Nobitex.TAG, "Done");
            // handler.sendMessage();

        } catch (Exception e) {
            Log.v(Nobitex.TAG, "ExceptionHappened in Get Trades Thread");
            e.printStackTrace();
        }
    }

    public static class TradeAnswer {
        private String status;
        ArrayList<Trade> trades = new ArrayList<Trade>();

        public TradeAnswer(String status, ArrayList<Trade> trades) {
            this.status = status;
            this.trades = trades;
        }

        // Getter Methods
        public ArrayList<Trade> getTrades() {
            return trades;
        }

        public String getStatus() {
            return status;
        }

        // Setter Methods
        public void setStatus(String status) {
            this.status = status;
        }

        public void setTrades(ArrayList<Trade> trades) {
            this.trades = trades;
        }

        public static class Trade {
            private float time;
            private String price;
            private String volume;
            private String type;


            public Trade(float time, String price, String volume, String type) {
                this.time = time;
                this.price = price;
                this.volume = volume;
                this.type = type;
            }

            // Getter Methods
            public float getTime() {
                return time;
            }

            public String getPrice() {
                return price;
            }

            public String getVolume() {
                return volume;
            }

            public String getType() {
                return type;
            }

            // Setter Methods
            public void setTime(float time) {
                this.time = time;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
