package com.example.nobintest.nobitex.dataTypes;

public class Wallet {
    private float id;
    private String user;
    private String currency;
    private String balance;
    private String blockedBalance;
    private String activeBalance;
    private float rialBalance;
    private float rialBalanceSell;
    private String depositAddress;
    private String depositTag;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getCurrency() {
        return currency;
    }

    public String getBalance() {
        return balance;
    }

    public String getBlockedBalance() {
        return blockedBalance;
    }

    public String getActiveBalance() {
        return activeBalance;
    }

    public float getRialBalance() {
        return rialBalance;
    }

    public float getRialBalanceSell() {
        return rialBalanceSell;
    }

    public String getDepositAddress() {
        return depositAddress;
    }

    public String getDepositTag() {
        return depositTag;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBlockedBalance(String blockedBalance) {
        this.blockedBalance = blockedBalance;
    }

    public void setActiveBalance(String activeBalance) {
        this.activeBalance = activeBalance;
    }

    public void setRialBalance(float rialBalance) {
        this.rialBalance = rialBalance;
    }

    public void setRialBalanceSell(float rialBalanceSell) {
        this.rialBalanceSell = rialBalanceSell;
    }

    public void setDepositAddress(String depositAddress) {
        this.depositAddress = depositAddress;
    }

    public void setDepositTag(String depositTag) {
        this.depositTag = depositTag;
    }
}