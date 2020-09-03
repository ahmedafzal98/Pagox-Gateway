package com.example.pagox;

import com.android.volley.toolbox.StringRequest;

public class LiveTransaction {
    String amount;
    String ref_id;
    String minutes;
    String TextCardHolder;
    String paymentTypeId;
    String last4digit;
    String status;
    String remAmount;
    String cardNumber;
    String cvv;
    String expYear;
    String expMonth;

    public LiveTransaction(String amount, String ref_id, String minutes, String TextCardHolder,String paymentTypeId,String last4digit,String status,String remAmount,String cardNumber,String cvv,String expMonth,String expYear) {
        this.amount = amount;
        this.ref_id = ref_id;
        this.minutes = minutes;
        this.TextCardHolder = TextCardHolder;
        this.paymentTypeId = paymentTypeId;
        this.last4digit = last4digit;
        this.status = status;
        this.remAmount = remAmount;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getRemAmount() {
        return remAmount;
    }

    public void setRemAmount(String remAmount) {
        this.remAmount = remAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getLast4digit() {
        return last4digit;
    }

    public void setLast4digit(String last4digit) {
        this.last4digit = last4digit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
    public String getTextCardHolder() {
        return TextCardHolder;
    }

    public void setTextCardHolder(String textCardHolder) {
        this.TextCardHolder = textCardHolder;
    }
}
