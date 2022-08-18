package com.example.nobintest.nobitex.dataTypes;

public class OrderStatus {
    private String unmatchedAmount;
    private String fee;
    private String matchedAmount;
    private boolean partial;
    private String price;
    private String created_at;
    private String user;
    private float id;
    private String srcCurrency;
    private String totalPrice;
    private String type;
    private String dstCurrency;
    private boolean isMyOrder;
    private String status;
    private String amount;


    // Getter Methods

    public String getUnmatchedAmount() {
        return unmatchedAmount;
    }

    public String getFee() {
        return fee;
    }

    public String getMatchedAmount() {
        return matchedAmount;
    }

    public boolean getPartial() {
        return partial;
    }

    public String getPrice() {
        return price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUser() {
        return user;
    }

    public float getId() {
        return id;
    }

    public String getSrcCurrency() {
        return srcCurrency;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getType() {
        return type;
    }

    public String getDstCurrency() {
        return dstCurrency;
    }

    public boolean getIsMyOrder() {
        return isMyOrder;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    // Setter Methods

    public void setUnmatchedAmount(String unmatchedAmount) {
        this.unmatchedAmount = unmatchedAmount;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setMatchedAmount(String matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDstCurrency(String dstCurrency) {
        this.dstCurrency = dstCurrency;
    }

    public void setIsMyOrder(boolean isMyOrder) {
        this.isMyOrder = isMyOrder;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
            "unmatchedAmount='" + unmatchedAmount + '\'' +
            ", fee='" + fee + '\'' +
            ", matchedAmount='" + matchedAmount + '\'' +
            ", partial=" + partial +
            ", price='" + price + '\'' +
            ", created_at='" + created_at + '\'' +
            ", user='" + user + '\'' +
            ", id=" + id +
            ", srcCurrency='" + srcCurrency + '\'' +
            ", totalPrice='" + totalPrice + '\'' +
            ", type='" + type + '\'' +
            ", dstCurrency='" + dstCurrency + '\'' +
            ", isMyOrder=" + isMyOrder +
            ", status='" + status + '\'' +
            ", amount='" + amount + '\'' +
            '}';
    }
}