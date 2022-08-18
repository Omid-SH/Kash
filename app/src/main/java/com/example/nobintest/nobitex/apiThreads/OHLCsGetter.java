package com.example.nobintest.nobitex.apiThreads;

import android.util.Log;

import com.example.nobintest.nobitex.Nobitex;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OHLCsGetter extends Thread {

    private String symbol;
    private String resolution;

    private String from;
    private String to;

    public OHLCsGetter(String symbol, String resolution, String from, String to) {
        this.symbol = symbol;
        this.resolution = resolution;
        this.from = from;
        this.to = to;
    }

    public void run() {
        Log.v(Nobitex.TAG, "StartGettingCandles(OHLC)");
        try {

            String request = "https://api.nobitex.ir/market/udf/history?" +
                "symbol=" + symbol +
                "&resolution=" + resolution +
                "&from=" + from +
                "&to=" + to;

            String result = Nobitex.get(request);
            OHLC ohlc = new Gson().fromJson(result, OHLC.class);
            Log.v(Nobitex.TAG, result);

            // handler.sendMessage()

        } catch (Exception e) {
            Log.v(Nobitex.TAG, "ExceptionHappened in Get OHLC Thread");
            e.printStackTrace();
        }
    }


    public static class OHLC {

        private String status;
        ArrayList<String> t;

        ArrayList<String> c;
        ArrayList<String> o;
        ArrayList<String> h;
        ArrayList<String> l;

        ArrayList<String> v;

        public OHLC(String status, ArrayList<String> t, ArrayList<String> c, ArrayList<String> o, ArrayList<String> h, ArrayList<String> l, ArrayList<String> v) {
            this.status = status;
            this.t = t;
            this.c = c;
            this.o = o;
            this.h = h;
            this.l = l;
            this.v = v;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArrayList<String> getT() {
            return t;
        }

        public void setT(ArrayList<String> t) {
            this.t = t;
        }

        public ArrayList<String> getC() {
            return c;
        }

        public void setC(ArrayList<String> c) {
            this.c = c;
        }

        public ArrayList<String> getO() {
            return o;
        }

        public void setO(ArrayList<String> o) {
            this.o = o;
        }

        public ArrayList<String> getH() {
            return h;
        }

        public void setH(ArrayList<String> h) {
            this.h = h;
        }

        public ArrayList<String> getL() {
            return l;
        }

        public void setL(ArrayList<String> l) {
            this.l = l;
        }

        public ArrayList<String> getV() {
            return v;
        }

        public void setV(ArrayList<String> v) {
            this.v = v;
        }
    }
}
