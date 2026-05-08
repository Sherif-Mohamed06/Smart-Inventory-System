package com.inventory.service;

import com.inventory.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryManager {
    private final List<Product> products = new ArrayList<>();
    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        
        for (Product existing : products) {
            if (existing.getSku().equalsIgnoreCase(product.getSku())) {
                return false;
            }
        }
        
        products.add(product);
        return true;
    }
    public Product findById(String id) {
        if (id == null) {
            return null;
        }
        
        for (Product product : products) {
            if (product.getSku().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }
        
        Product product = findById(id);
        if (product == null) {
            return false;
        }
        
        return products.remove(product);
    }
    public List<Product> getLowStockProducts() {
        List<Product> lowStockList = new ArrayList<>();
        for (Product product : products) {
            if (product.isLowStock()) {
                lowStockList.add(product);
            }
        }
        return lowStockList;
    }
    public int getTotalQuantity() {
        int total = 0;
        for (Product product : products) {
            total += product.getQuantity();
        }
        return total;
    }
    public boolean updateProduct(String id, int quantity, int threshold) {
        Product product = findById(id);
        if (product == null) {
            return false;
        }
        product.setQuantity(quantity);
        product.setThreshold(threshold);
        return true;
    }
}
