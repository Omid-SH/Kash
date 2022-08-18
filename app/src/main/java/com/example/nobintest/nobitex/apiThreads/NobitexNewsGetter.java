package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class NobitexNewsGetter extends Thread {

    private String src;
    private String dst;
    private Handler handler;

    public NobitexNewsGetter(String src, String dst, Handler handler) {
        this.src = src;
        this.dst = dst;
        this.handler = handler;
    }

    public void run() {
        super.run();
        Log.v(TAG, "startGettingNews");

        try {

            String result = Nobitex.post("https://api.nobitex.ir/market/stats",
                "{\"srcCurrency\":\"" + src + "\",\"dstCurrency\":\"" + dst + "\"}");

            JSONObject response = new JSONObject(result);
            String status = (String) response.get("status");

            String stats = response.getJSONObject("stats").toString();
            stats = stats.substring(stats.indexOf(":") + 1, stats.length() - 1);

            NobitexNewsAnswer nobitexNewsAnswer = new Gson().fromJson(stats, NobitexNewsAnswer.class);

            // price of global coins in USD
            JSONObject global = response.getJSONObject("global");

            Log.v(TAG, status);
            Log.v(TAG, stats);
            Log.v(TAG, global.toString());

            Message message = new Message();
            message.what = DataManager.NOBITEXT_NEWS_GOT;

            if (status.equals("ok")) {
                message.obj = nobitexNewsAnswer;
            } else {
                message.obj = null;
            }
            handler.sendMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class NobitexNewsAnswer {

        private boolean isClosed;
        private String bestSell;
        private String bestBuy;
        private String volumeSrc;
        private String volumeDst;
        private String latest;
        private String dayLow;
        private String dayHigh;
        private String dayOpen;
        private String dayClose;
        private String dayChange;

        public NobitexNewsAnswer(boolean isClosed, String bestSell, String bestBuy, String volumeSrc, String volumeDst, String latest, String dayLow, String dayHigh, String dayOpen, String dayClose, String dayChange) {
            this.isClosed = isClosed;
            this.bestSell = bestSell;
            this.bestBuy = bestBuy;
            this.volumeSrc = volumeSrc;
            this.volumeDst = volumeDst;
            this.latest = latest;
            this.dayLow = dayLow;
            this.dayHigh = dayHigh;
            this.dayOpen = dayOpen;
            this.dayClose = dayClose;
            this.dayChange = dayChange;
        }


        // Getter Methods

        public boolean getIsClosed() {
            return isClosed;
        }

        public String getBestSell() {
            return bestSell;
        }

        public String getBestBuy() {
            return bestBuy;
        }

        public String getVolumeSrc() {
            return volumeSrc;
        }

        public String getVolumeDst() {
            return volumeDst;
        }

        public String getLatest() {
            return latest;
        }

        public String getDayLow() {
            return dayLow;
        }

        public String getDayHigh() {
            return dayHigh;
        }

        public String getDayOpen() {
            return dayOpen;
        }

        public String getDayClose() {
            return dayClose;
        }

        public String getDayChange() {
            return dayChange;
        }

        // Setter Methods

        public void setIsClosed(boolean isClosed) {
            this.isClosed = isClosed;
        }

        public void setBestSell(String bestSell) {
            this.bestSell = bestSell;
        }

        public void setBestBuy(String bestBuy) {
            this.bestBuy = bestBuy;
        }

        public void setVolumeSrc(String volumeSrc) {
            this.volumeSrc = volumeSrc;
        }

        public void setVolumeDst(String volumeDst) {
            this.volumeDst = volumeDst;
        }

        public void setLatest(String latest) {
            this.latest = latest;
        }

        public void setDayLow(String dayLow) {
            this.dayLow = dayLow;
        }

        public void setDayHigh(String dayHigh) {
            this.dayHigh = dayHigh;
        }

        public void setDayOpen(String dayOpen) {
            this.dayOpen = dayOpen;
        }

        public void setDayClose(String dayClose) {
            this.dayClose = dayClose;
        }

        public void setDayChange(String dayChange) {
            this.dayChange = dayChange;
        }
    }


}
