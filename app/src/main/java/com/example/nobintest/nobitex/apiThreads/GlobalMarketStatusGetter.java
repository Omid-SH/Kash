package com.example.nobintest.nobitex.apiThreads;

import android.util.Log;

import com.example.nobintest.nobitex.Nobitex;

import static android.content.ContentValues.TAG;

public class GlobalMarketStatusGetter extends Thread {

    @Override
    public void run() {
        super.run();

        Log.v(TAG, "startGettingGlobalStat");
        String result = Nobitex.post("https://api.nobitex.ir/market/global-stats", null);
        // getMarkets
        Log.v(TAG, result);

    }

}
