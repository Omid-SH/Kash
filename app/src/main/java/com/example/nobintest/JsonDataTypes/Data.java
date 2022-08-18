package com.example.nobintest.JsonDataTypes;

public class Data {
    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    private float is_active;
    private String first_historical_data;
    private String last_historical_data;
    private Platform PlatformObject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public float getIs_active() {
        return is_active;
    }

    public void setIs_active(float is_active) {
        this.is_active = is_active;
    }

    public String getFirst_historical_data() {
        return first_historical_data;
    }

    public void setFirst_historical_data(String first_historical_data) {
        this.first_historical_data = first_historical_data;
    }

    public String getLast_historical_data() {
        return last_historical_data;
    }

    public void setLast_historical_data(String last_historical_data) {
        this.last_historical_data = last_historical_data;
    }

    public Platform getPlatformObject() {
        return PlatformObject;
    }

    public void setPlatformObject(Platform platformObject) {
        PlatformObject = platformObject;
    }
}

