package com.example.nobintest.graphActivity.candlaDataSets;

import java.util.List;

public class LatestCandleData {
    List<Candle> candleList;

    public LatestCandleData(List<Candle> candleList) {
        this.candleList = candleList;
    }

    public List<Candle> getCandleList() {
        return candleList;
    }

    public void setCandleList(List<Candle> candleList) {
        this.candleList = candleList;
    }
}
