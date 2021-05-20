package com.example.covid_app;

import com.google.gson.annotations.SerializedName;

public class CentersPojo {

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("name")
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
