package com.example.nobintest.nobitex.apiThreads;

import com.example.nobintest.nobitex.Nobitex;
import com.google.gson.Gson;

public class BalanceGetter extends Thread {
    private String currency;

    BalanceGetter(String currency) {
        this.currency = currency;
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

        String json = "{\"currency\":\"" + currency + "\"}";
        String result = Nobitex.post("https://api.nobitex.ir/users/wallets/balance",
            json, token);

        Balance balance = new Gson().fromJson(result, Balance.class);
        // handler.sendMessage();

    }

    public static class Balance {

        private String balance;
        private String status;

        // Getter Methods

        public String getBalance() {
            return balance;
        }

        public String getStatus() {
            return status;
        }

        // Setter Methods

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
