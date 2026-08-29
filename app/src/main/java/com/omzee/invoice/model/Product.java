package com.omzee.invoice.model;

public class Product {

    private String description;
    private String hsn;

    public Product(String description, String hsn) {
        this.description = description;
        this.hsn = hsn;
    }

    public String getDescription() {
        return description;
    }

    public String getHsn() {
        return hsn;
    }
}