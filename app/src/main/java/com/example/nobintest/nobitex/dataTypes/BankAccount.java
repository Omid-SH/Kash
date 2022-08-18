package com.example.nobintest.nobitex.dataTypes;

public class BankAccount {

    private float id;
    private String number;
    private String shaba;
    private String bank;
    private String owner;
    private boolean confirmed;
    private String status;

    public BankAccount(float id, String number, String shaba, String bank, String owner, boolean confirmed, String status) {
        this.id = id;
        this.number = number;
        this.shaba = shaba;
        this.bank = bank;
        this.owner = owner;
        this.confirmed = confirmed;
        this.status = status;
    }

    public float getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getShaba() {
        return shaba;
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

    public void setId(float id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setShaba(String shaba) {
        this.shaba = shaba;
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
        return "BankAccount{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", shaba='" + shaba + '\'' +
            ", bank='" + bank + '\'' +
            ", owner='" + owner + '\'' +
            ", confirmed=" + confirmed +
            ", status='" + status + '\'' +
            '}';
    }
}