package com.example.covid_app;

import com.google.gson.annotations.SerializedName;

public class StatusPojo {

    @SerializedName("mailID")
    private String mailID;

    @SerializedName("name")
    private String name;

    @SerializedName("phnoNo")
    private String phnoNo;

    @SerializedName("status")
    private String status;

    @SerializedName("center")
    private String center;



    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    @SerializedName("date")
    private String date;

    @SerializedName("username")
    private String username;

    @SerializedName("booking_date")
    private String booking_date;

    public StatusPojo(String mailID, String name, String phnoNo, String status, String date, String username, String booking_date, String center) {
        this.mailID = mailID;
        this.name = name;
        this.phnoNo = phnoNo;
        this.status = status;
        this.date = date;
        this.username = username;
        this.booking_date = booking_date;
        this.center = center;
    }

    public StatusPojo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhnoNo() {
        return phnoNo;
    }

    public void setPhnoNo(String phnoNo) {
        this.phnoNo = phnoNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
