package com.example.nobintest.nobitex.dataTypes;

public class BankCard {

    private String number;
    private String bank;
    private String owner;
    private boolean confirmed;
    private String status;

    public BankCard(String number, String bank, String owner, boolean confirmed, String status) {
        this.number = number;
        this.bank = bank;
        this.owner = owner;
        this.confirmed = confirmed;
        this.status = status;
    }

    // Getter Methods

    public String getNumber() {
        return number;
    }

    public String getBank() {
        return bank;
    }

    public String getOwner() {
        return owner;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BankCard{" +
            "number='" + number + '\'' +
            ", bank='" + bank + '\'' +
            ", owner='" + owner + '\'' +
            ", confirmed=" + confirmed +
            ", status='" + status + '\'' +
            '}';
    }
}