package com.example.nobintest.nobitex.dataTypes;

public class TradeStats {

    private String monthTradesTotal;
    private float monthTradesCount;


    // Getter Methods

    public String getMonthTradesTotal() {
        return monthTradesTotal;
    }

    public float getMonthTradesCount() {
        return monthTradesCount;
    }

    // Setter Methods

    public void setMonthTradesTotal(String monthTradesTotal) {
        this.monthTradesTotal = monthTradesTotal;
    }

    public void setMonthTradesCount(float monthTradesCount) {
        this.monthTradesCount = monthTradesCount;
    }

    @Override
    public String toString() {
        return "TradeStats{" +
            "monthTradesTotal='" + monthTradesTotal + '\'' +
            ", monthTradesCount=" + monthTradesCount +
            '}';
    }
}