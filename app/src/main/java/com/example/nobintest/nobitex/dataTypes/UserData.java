package com.example.nobintest.nobitex.dataTypes;

import java.util.ArrayList;

public class UserData {
    private String id;
    private String username;
    private String email;
    private String level;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String nickname;
    private String phone;
    private String mobile;
    private String province;
    private String city;
    private ArrayList<BankCard> bankCards;
    private ArrayList<BankAccount> bankAccounts;
    private Verifications verifications;
    private PendingVerifications pendingVerifications;
    private Options options;

    // private boolean withdrawEligible;
    private String chatId;

    public UserData(String id, String username, String email, String level, String firstName, String lastName, String nationalCode, String nickname, String phone, String mobile, String province, String city, ArrayList<BankCard> bankCards, ArrayList<BankAccount> bankAccounts, Verifications verifications, PendingVerifications pendingVerifications, Options options, String chatId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.level = level;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.nickname = nickname;
        this.phone = phone;
        this.mobile = mobile;
        this.province = province;
        this.city = city;
        this.bankCards = bankCards;
        this.bankAccounts = bankAccounts;
        this.verifications = verifications;
        this.pendingVerifications = pendingVerifications;
        this.options = options;
        this.chatId = chatId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(ArrayList<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Verifications getVerifications() {
        return verifications;
    }

    public void setVerifications(Verifications verifications) {
        this.verifications = verifications;
    }

    public PendingVerifications getPendingVerifications() {
        return pendingVerifications;
    }

    public void setPendingVerifications(PendingVerifications pendingVerifications) {
        this.pendingVerifications = pendingVerifications;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", level='" + level + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", nationalCode='" + nationalCode + '\'' +
            ", nickname='" + nickname + '\'' +
            ", phone='" + phone + '\'' +
            ", mobile='" + mobile + '\'' +
            ", province='" + province + '\'' +
            ", city='" + city + '\'' +
            ", bankCards=" + bankCards +
            ", bankAccounts=" + bankAccounts +
            ", verifications=" + verifications +
            ", pendingVerifications=" + pendingVerifications +
            ", options=" + options +
            ", chatId='" + chatId + '\'' +
            '}';
    }
}

