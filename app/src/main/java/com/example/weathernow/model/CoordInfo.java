package com.example.weathernow.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoordInfo{
    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
