package com.example.myapplication.models;

public class BookingModel
{

// getters and setters
    String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    String to;
    String date;
    String numberOfPersons;
    String totalAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;
    public BookingModel(String from,String to,String date,String numberOfPersons,String totalAmount,String userId)
    {
        // constructor
        this.from=from;
        this.to = to;
        this.date=date;
        this.numberOfPersons = numberOfPersons;
        this.totalAmount=totalAmount;
        this.userId=userId;
    }
}
