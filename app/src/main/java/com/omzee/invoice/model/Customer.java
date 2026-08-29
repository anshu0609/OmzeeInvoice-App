package com.omzee.invoice.model;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String gst;
    private String state;

    public Customer() {
    }

    public Customer(int id, String name, String address, String gst, String state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gst = gst;
        this.state = state;
    }

    public int getId() {
        return id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}