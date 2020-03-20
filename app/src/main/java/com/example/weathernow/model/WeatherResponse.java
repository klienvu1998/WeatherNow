package com.example.weathernow.model;

import java.util.ArrayList;

public class WeatherResponse{
    private CoordInfo coord;
    private ArrayList<WeatherInfo> weather;
    private MainInfo main;
    private String dt;

    public String getDt() {
        return dt;
    }


    public ArrayList<WeatherInfo> getWeather() {
        return weather;
    }


    public MainInfo getMain() {
        return main;
    }

}
