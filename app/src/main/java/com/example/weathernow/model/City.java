package com.example.weathernow.model;

import java.io.Serializable;

public class City implements Serializable {
    private String id;
    private String name;


    public City(String id, String name) {
        this.id = id;
        this.name = name;
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
