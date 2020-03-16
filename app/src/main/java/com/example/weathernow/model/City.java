package com.example.weathernow.model;

import java.io.Serializable;

public class City implements Serializable {
    private String id;
    private String name;
    private String temp;
    private String status;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
