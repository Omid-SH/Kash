package com.example.nobintest.nobitex.apiThreads;

import android.os.Handler;
import android.os.Message;

import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.nobitex.Nobitex;
import com.example.nobintest.nobitex.dataTypes.Wallet;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WalletGetter extends Thread {

    private Handler handler;

    public WalletGetter(Handler handler) {
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

        String result = Nobitex.post("https://api.nobitex.ir/users/wallets/list",
            null, token);

        WalletAnswer walletAnswer = new Gson().fromJson(result, WalletAnswer.class);

        Message message = new Message();
        message.what = DataManager.WALLETS_GOT;

        if (walletAnswer.status.equals("ok")) {
            message.obj = walletAnswer.getWallets();
        } else {
            message.obj = null;
        }
        handler.sendMessage(message);

    }


    public static class WalletAnswer {
        private String status;
        private ArrayList<Wallet> wallets;

        public WalletAnswer(String status, ArrayList<Wallet> wallets) {
            this.status = status;
            this.wallets = wallets;
        }

        public String getStatus() {
            return status;
        }

        public ArrayList<Wallet> getWallets() {
            return wallets;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setWallets(ArrayList<Wallet> wallets) {
            this.wallets = wallets;
        }
    }


}
