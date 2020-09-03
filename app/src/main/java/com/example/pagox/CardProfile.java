package com.example.pagox;

public class CardProfile {

    int profileId;
    String cardHoldername;
    String cardNumber;
    String tenderType;
    String paymentType;
    String Date;

    public CardProfile(int profileId, String cardHoldername, String cardNumber, String tenderType, String paymentType, String date) {
        this.profileId = profileId;
        this.cardHoldername = cardHoldername;
        this.cardNumber = cardNumber;
        this.tenderType = tenderType;
        this.paymentType = paymentType;
        Date = date;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getCardHoldername() {
        return cardHoldername;
    }

    public void setCardHoldername(String cardHoldername) {
        this.cardHoldername = cardHoldername;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
