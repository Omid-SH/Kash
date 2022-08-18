package com.example.nobintest.nobitex.dataTypes;

public class PendingVerifications {

    private boolean email;
    private boolean phone;
    private boolean mobile;
    private boolean identity;
    private boolean selfie;
    private boolean bankAccount;
    private boolean bankCard;

    // Getter Methods

    public boolean getEmail() {
        return email;
    }

    public boolean getPhone() {
        return phone;
    }

    public boolean getMobile() {
        return mobile;
    }

    public boolean getIdentity() {
        return identity;
    }

    public boolean getSelfie() {
        return selfie;
    }

    public boolean getBankAccount() {
        return bankAccount;
    }

    public boolean getBankCard() {
        return bankCard;
    }

    // Setter Methods

    public void setEmail(boolean email) {
        this.email = email;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public void setSelfie(boolean selfie) {
        this.selfie = selfie;
    }

    public void setBankAccount(boolean bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setBankCard(boolean bankCard) {
        this.bankCard = bankCard;
    }

    @Override
    public String toString() {
        return "PendingVerifications{" +
            "email=" + email +
            ", phone=" + phone +
            ", mobile=" + mobile +
            ", identity=" + identity +
            ", selfie=" + selfie +
            ", bankAccount=" + bankAccount +
            ", bankCard=" + bankCard +
            '}';
    }
}