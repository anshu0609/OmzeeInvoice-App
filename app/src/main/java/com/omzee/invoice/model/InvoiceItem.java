package com.omzee.invoice.model;

public class InvoiceItem {

    private String productDescription;
    private String hsn;
    private double quantity;
    private double rate;
    private double amount;

    public InvoiceItem() {
    }

    public InvoiceItem(String productDescription, String hsn,
                       double quantity, double rate, double amount) {

        this.productDescription = productDescription;
        this.hsn = hsn;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}