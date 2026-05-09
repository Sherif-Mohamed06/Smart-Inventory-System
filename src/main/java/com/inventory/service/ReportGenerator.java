package com.inventory.service;

import com.inventory.model.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {
    private static final String DATE_FMT = "yyyy-MM-dd HH:mm:ss";
    private static final String DIVIDER = "=".repeat(70);

    public static void printReport(List<Product> products, String title, int totalItems) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FMT);
        String timestamp = LocalDateTime.now().format(formatter);

        System.out.println(DIVIDER);
        System.out.println("Smart Inventory Management System");
        System.out.println(title);
        System.out.println("Generated: " + timestamp);
        System.out.println("Total products in system: " + totalItems);
        System.out.println(DIVIDER);

        if (products.isEmpty()) {
            System.out.println("No products to display.");
            System.out.println(DIVIDER);
            return;
        }

        System.out.printf("%-4s %-10s %-20s %8s %9s %-10s%n", "#", "SKU", "Name", "Qty", "Threshold", "Status");
        System.out.println("-".repeat(70));

        int rowNum = 1;
        int totalQuantity = 0;
        int lowStockCount = 0;

        for (Product product : products) {
            System.out.printf("%-4d %-10s %-20s %8d %9d %-10s%n",
                    rowNum++,
                    product.getSku(),
                    product.getName(),
                    product.getQuantity(),
                    product.getThreshold(),
                    product.isLowStock() ? "LOW STOCK" : "OK");

            totalQuantity += product.getQuantity();
            if (product.isLowStock()) {
                lowStockCount++;
            }
        }

        System.out.println("-".repeat(70));
        System.out.printf("Products shown: %d | Total qty: %d | Low-stock: %d%n", 
                products.size(), totalQuantity, lowStockCount);
        System.out.println(DIVIDER);
    }
}
