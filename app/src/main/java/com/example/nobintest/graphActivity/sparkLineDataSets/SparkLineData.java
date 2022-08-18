package com.example.nobintest.graphActivity.sparkLineDataSets;

import java.util.ArrayList;

public class SparkLineData {

    private String currency;
    ArrayList<String> timestamps;
    ArrayList<Float> prices;

    public SparkLineData(String currency, ArrayList<String> timestamps, ArrayList<Float> prices) {
        this.currency = currency;
        this.timestamps = timestamps;
        this.prices = prices;
    }

    public ArrayList<Float> getPrices() {
        return prices;
    }

    public String getCurrency() {
        return currency;
    }

    public ArrayList<String> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(ArrayList<String> timestamps) {
        this.timestamps = timestamps;
    }

    public void setPrices(ArrayList<Float> prices) {
        this.prices = prices;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}