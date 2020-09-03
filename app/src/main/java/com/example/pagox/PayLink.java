package com.example.pagox;

public class PayLink {
    String date;
    String expire;
    String cancel;
    String payDate;
    String paid;
    int accessCode;
    String payLink;

    public PayLink(String date, String expire, String cancel, String payDate, String paid, int accessCode, String payLink) {
        this.date = date;
        this.expire = expire;
        this.cancel = cancel;
        this.payDate = payDate;
        this.paid = paid;
        this.accessCode = accessCode;
        this.payLink = payLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public String getPayLink() {
        return payLink;
    }

    public void setPayLink(String payLink) {
        this.payLink = payLink;
    }
}
