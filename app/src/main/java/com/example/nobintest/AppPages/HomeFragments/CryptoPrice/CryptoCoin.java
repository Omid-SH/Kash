package com.example.nobintest.AppPages.HomeFragments.CryptoPrice;


public class CryptoCoin {
    private String id;
    private String name;
    private String symbol;

    private String price_usd;
    private String percentChange1h;
    private String percentChange1d;
    private String percentChange7d;

    public CryptoCoin(String id, String name, String symbol, String price_usd, String percentChange1h, String percentChange24h, String percentChange7d) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price_usd = price_usd;
        this.percentChange1h = percentChange1h;
        this.percentChange1d = percentChange24h;
        this.percentChange7d = percentChange7d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
    }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(String percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange24h() {
        return percentChange1d;
    }

    public void setPercentChange24h(String percentChange24h) {
        this.percentChange1d = percentChange24h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(String percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoCoin that = (CryptoCoin) o;
        return id.equals(that.id);
    }

}
