package com.example.nobintest.graphActivity.candlaDataSets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Candle implements Comparable {

    private String time_period_start;
    private String time_period_end;
    private String time_open;
    private String time_close;
    private float price_open;
    private float price_high;
    private float price_low;
    private float price_close;
    private float volume_traded;
    private int trades_count;

    public Candle(String time_period_start,
                  String time_period_end,
                  String time_open,
                  String time_close,
                  float price_open,
                  float price_high,
                  float price_low,
                  float price_close,
                  float volume_traded,
                  int trades_count) {

        this.time_period_start = time_period_start;
        this.time_period_end = time_period_end;
        this.time_open = time_open;
        this.time_close = time_close;
        this.price_open = price_open;
        this.price_high = price_high;
        this.price_low = price_low;
        this.price_close = price_close;
        this.volume_traded = volume_traded;
        this.trades_count = trades_count;

    }

    // Getter Methods

    public String getTime_period_start() {
        return time_period_start;
    }

    public String getTime_period_end() {
        return time_period_end;
    }

    public String getTime_open() {
        return time_open;
    }

    public String getTime_close() {
        return time_close;
    }

    public float getPrice_open() {
        return price_open;
    }

    public float getPrice_high() {
        return price_high;
    }

    public float getPrice_low() {
        return price_low;
    }

    public float getPrice_close() {
        return price_close;
    }

    public float getVolume_traded() {
        return volume_traded;
    }

    public int getTrades_count() {
        return trades_count;
    }

    // Setter Methods

    public void setTime_period_start(String time_period_start) {
        this.time_period_start = time_period_start;
    }

    public void setTime_period_end(String time_period_end) {
        this.time_period_end = time_period_end;
    }

    public void setTime_open(String time_open) {
        this.time_open = time_open;
    }

    public void setTime_close(String time_close) {
        this.time_close = time_close;
    }

    public void setPrice_open(float price_open) {
        this.price_open = price_open;
    }

    public void setPrice_high(float price_high) {
        this.price_high = price_high;
    }

    public void setPrice_low(float price_low) {
        this.price_low = price_low;
    }

    public void setPrice_close(float price_close) {
        this.price_close = price_close;
    }

    public void setVolume_traded(float volume_traded) {
        this.volume_traded = volume_traded;
    }

    public void setTrades_count(int trades_count) {
        this.trades_count = trades_count;
    }

    @Override
    public String toString() {
        return "Candle{" +
            "time_period_start='" + time_period_start + '\'' +
            ", time_period_end='" + time_period_end + '\'' +
            ", time_open='" + time_open + '\'' +
            ", time_close='" + time_close + '\'' +
            ", price_open=" + price_open +
            ", price_high=" + price_high +
            ", price_low=" + price_low +
            ", price_close=" + price_close +
            ", volume_traded=" + volume_traded +
            ", trades_count=" + trades_count +
            '}';
    }


    public float min() {
        return Math.min(Math.min(Math.min(price_open, price_close), price_high), price_low);
    }

    public float max() {
        return Math.max(Math.max(Math.max(price_open, price_close), price_high), price_low);
    }


    @Override
    public int compareTo(Object o) {

        String thisTime = getStandardDate();
        String thatTime = ((Candle) o).getStandardDate();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(thisTime);
            d2 = format.parse(thatTime);
            int answer = d1.compareTo(d2);
            return answer;

        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private String getStandardDate() {
        String time1 = time_period_start.substring(0, time_period_start.indexOf("T"));
        String time2 = time_period_start.substring(time_period_start.indexOf("T") + 1, time_period_start.indexOf("."));

        return time1.concat(" ").concat(time2);
    }
}