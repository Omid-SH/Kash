package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.example.nobintest.nobitex.dataTypes.TradeStats;
import com.example.nobintest.nobitex.dataTypes.UserData;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

public class ProfileGetter extends Thread {

    private Handler handler;

    public ProfileGetter(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        Log.v(TAG, "StartGettingProfile");

        String request = "https://api.nobitex.ir/users/profile";
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

        String result = Nobitex.get(request, token);

        ProfileAnswer profile = new Gson().fromJson(result, ProfileAnswer.class);

        Message message = new Message();
        message.what = DataManager.PROFILE_GOT;

        if (profile.status.equals("ok")) {
            message.obj = profile.getUserData();
        } else {
            message.obj = null;
        }
        handler.sendMessage(message);

    }

    public static class ProfileAnswer {
        private String status;
        private UserData profile;
        private TradeStats tradeStats;


        public ProfileAnswer(String status, UserData profile, TradeStats tradeStats) {
            this.status = status;
            this.profile = profile;
            this.tradeStats = tradeStats;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public UserData getUserData() {
            return profile;
        }

        public void setUserData(UserData userData) {
            this.profile = userData;
        }

        public TradeStats getTradeStats() {
            return tradeStats;
        }

        public void setTradeStats(TradeStats tradeStats) {
            this.tradeStats = tradeStats;
        }
    }
}
