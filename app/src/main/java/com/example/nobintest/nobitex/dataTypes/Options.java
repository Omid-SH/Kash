package com.example.nobintest.nobitex.dataTypes;

public class Options {

    private String fee;
    private String feeUsdt;
    private boolean isManualFee;
    private boolean tfa;
    private boolean socialLoginEnabled;

    // Getter Methods
    public String getFee() {
        return fee;
    }

    public String getFeeUsdt() {
        return feeUsdt;
    }

    public boolean getIsManualFee() {
        return isManualFee;
    }

    public boolean getTfa() {
        return tfa;
    }

    public boolean getSocialLoginEnabled() {
        return socialLoginEnabled;
    }

    // Setter Methods

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setFeeUsdt(String feeUsdt) {
        this.feeUsdt = feeUsdt;
    }

    public void setIsManualFee(boolean isManualFee) {
        this.isManualFee = isManualFee;
    }

    public void setTfa(boolean tfa) {
        this.tfa = tfa;
    }

    public void setSocialLoginEnabled(boolean socialLoginEnabled) {
        this.socialLoginEnabled = socialLoginEnabled;
    }

    @Override
    public String toString() {
        return "Options{" +
            "fee='" + fee + '\'' +
            ", feeUsdt='" + feeUsdt + '\'' +
            ", isManualFee=" + isManualFee +
            ", tfa=" + tfa +
            ", socialLoginEnabled=" + socialLoginEnabled +
            '}';
    }
}