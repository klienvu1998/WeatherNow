package com.example.weathernow.model;

public class WeatherResponse {
    private String temp;

    public WeatherResponse(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
