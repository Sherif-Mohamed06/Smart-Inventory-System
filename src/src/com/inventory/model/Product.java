package com.inventory.model;

public class Product {
    private String sku;
    private String name;
    private int quantity;
    private int threshold;

    // Constructor
    public Product(String sku, String name, int quantity, int threshold) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    // Getters
    public String getSku() { return sku; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getThreshold() { return threshold; }

    // Setters
    public void setSku(String sku) { this.sku = sku; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setThreshold(int threshold) { this.threshold = threshold; }

    // Business logic method
    public boolean isLowStock() {
        return quantity <= threshold;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | %5d | %5d | %s",
                sku, name, quantity, threshold, isLowStock() ? "LOW STOCK" : "OK");
    }
}
