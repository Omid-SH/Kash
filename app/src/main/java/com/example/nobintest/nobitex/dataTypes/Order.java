package com.example.nobintest.nobitex.dataTypes;

public class Order {

    private String type;
    private String srcCurrency;
    private String dstCurrency;
    private String price;
    private String amount;
    private String totalPrice;
    private float matchedAmount;
    private String unmatchedAmount;
    private float id;
    private String status;
    private boolean partial;
    private float fee;
    private String user;
    private String created_at;


    // Getter Methods

    public String getType() {
        return type;
    }

    public String getSrcCurrency() {
        return srcCurrency;
    }

    public String getDstCurrency() {
        return dstCurrency;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public float getMatchedAmount() {
        return matchedAmount;
    }

    public String getUnmatchedAmount() {
        return unmatchedAmount;
    }

    public float getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public boolean getPartial() {
        return partial;
    }

    public float getFee() {
        return fee;
    }

    public String getUser() {
        return user;
    }

    public String getCreated_at() {
        return created_at;
    }

    // Setter Methods

    public void setType(String type) {
        this.type = type;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }

    public void setDstCurrency(String dstCurrency) {
        this.dstCurrency = dstCurrency;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMatchedAmount(float matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public void setUnmatchedAmount(String unmatchedAmount) {
        this.unmatchedAmount = unmatchedAmount;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

