package com.example.nobintest.JsonDataTypes;

public class Platform {
    private float id;
    private String name;
    private String symbol;
    private String slug;
    private String token_address;

    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSlug() {
        return slug;
    }

    public String getToken_address() {
        return token_address;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setToken_address(String token_address) {
        this.token_address = token_address;
    }
}