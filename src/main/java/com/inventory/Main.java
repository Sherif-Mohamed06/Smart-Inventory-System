package com.inventory;

import com.inventory.model.Product;
import com.inventory.service.InventoryManager;
import com.inventory.service.ReportGenerator;
import com.inventory.model.invalidInput.InputValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private final InventoryManager inventory = new InventoryManager();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        while (true) {
            showMenu();
            int choice = getIntInput();
            if (!InputValidator.isValidMenuChoice(choice, 7)) {
                System.out.println("Invalid option. Please enter 1-7.");
                continue;
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    viewLowStockProducts();
                    break;
                case 6:
                    generateReport();
                    break;
                case 7:
                    if (confirmExit()) {
                        scanner.close();
                        System.out.println("Thank you for using Smart Inventory System. Goodbye.");
                        return;
                    }
                    break;
            }
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("================================================================");
        System.out.println("SMART INVENTORY SYSTEM");
        System.out.println("================================================================");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. View Low-Stock Products");
        System.out.println("6. Generate Report");
        System.out.println("7. Exit");
        System.out.println("================================================================");
    }

    private int getIntInput() {
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                return choice;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear the invalid input
                System.out.println("Invalid input, please enter a number.");
            }
        }
    }

    private void addProduct() {
        System.out.println("--- ADD NEW PRODUCT ---");
        String id = readNonEmptyString("Enter product ID");
        if (!InputValidator.isValidId(id)) {
            System.out.println("Invalid ID. ID cannot be blank.");
            return;
        }

        String name = readNonEmptyString("Enter product name");
        if (!InputValidator.isNotBlank(name)) {
            System.out.println("Invalid name. Name cannot be blank.");
            return;
        }

        int quantity = readInt("Enter quantity", 0, Integer.MAX_VALUE);
        if (!InputValidator.isValidQuantity(quantity)) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        int threshold = readInt("Enter low-stock threshold", 1, Integer.MAX_VALUE);
        if (!InputValidator.isValidThreshold(threshold)) {
            System.out.println("Threshold must be at least 1.");
            return;
        }

        Product product = new Product(id, name, quantity, threshold);
        if (inventory.addProduct(product)) {
            System.out.println("Product added successfully.");
            System.out.println("  ID: " + id);
            System.out.println("  Name: " + name);
            System.out.println("  Quantity: " + quantity);
            System.out.println("  Threshold: " + threshold);
        } else {
            System.out.println("Product with this ID already exists.");
        }
    }

    private void viewProducts() {
        System.out.println("--- ALL PRODUCTS ---");
        if (inventory.getAllProducts().isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }

        System.out.printf("%-4s %-10s %-20s %8s %9s %-10s%n", "#", "SKU", "Name", "Qty", "Threshold", "Status");
        System.out.println("-".repeat(70));

        int rowNum = 1;
        for (Product product : inventory.getAllProducts()) {
            System.out.printf("%-4d %-10s %-20s %8d %9d %-10s%n",
                    rowNum++,
                    product.getSku(),
                    product.getName(),
                    product.getQuantity(),
                    product.getThreshold(),
                    product.isLowStock() ? "LOW STOCK" : "OK");
        }
    }

    private void updateProduct() {
        System.out.println("--- UPDATE PRODUCT ---");
        String id = readNonEmptyString("Enter product ID to update");
        Product product = inventory.findById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Current values:");
        System.out.println("  Name: " + product.getName());
        System.out.println("  Quantity: " + product.getQuantity());
        System.out.println("  Threshold: " + product.getThreshold());

        int quantity = readInt("Enter new quantity", 0, Integer.MAX_VALUE);
        int threshold = readInt("Enter new low-stock threshold", 1, Integer.MAX_VALUE);

        if (inventory.updateProduct(id, quantity, threshold)) {
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private void deleteProduct() {
        System.out.println("--- DELETE PRODUCT ---");
        String id = readNonEmptyString("Enter product ID to delete");
        Product product = inventory.findById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Are you sure you want to delete this product? (Y/N): ");
        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("Y")) {
            if (inventory.deleteById(id)) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private void viewLowStockProducts() {
        System.out.println("--- LOW-STOCK PRODUCTS ---");
        var lowStockList = inventory.getLowStockProducts();
        if (lowStockList.isEmpty()) {
            System.out.println("No low-stock products.");
            return;
        }

        System.out.printf("%-4s %-10s %-20s %8s %9s %-10s%n", "#", "ID", "Name", "Qty", "Threshold", "Status");
        System.out.println("-".repeat(70));

        int rowNum = 1;
        for (Product product : lowStockList) {
            System.out.printf("%-4d %-10s %-20s %8d %9d %-10s%n",
                    rowNum++,
                    product.getSku(),
                    product.getName(),
                    product.getQuantity(),
                    product.getThreshold(),
                    product.isLowStock() ? "LOW STOCK" : "OK");
        }
    }

    private void generateReport() {
        System.out.println("--- GENERATE REPORT ---");
        System.out.println("1. Full Inventory Report");
        System.out.println("2. Low-Stock Report");
        System.out.print("Select report type (1 or 2): ");

        int reportType = 0;
        try {
            reportType = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            System.out.println("Invalid input.");
            return;
        }

        if (reportType == 1) {
            ReportGenerator.printReport(inventory.getAllProducts(), "FULL INVENTORY REPORT", inventory.getAllProducts().size());
        } else if (reportType == 2) {
            ReportGenerator.printReport(inventory.getLowStockProducts(), "LOW-STOCK REPORT", inventory.getAllProducts().size());
        } else {
            System.out.println("Invalid report type.");
        }
    }

    private boolean confirmExit() {
        System.out.print("Are you sure you want to exit? (Y/N): ");
        String answer = scanner.nextLine().trim();
        return answer.equalsIgnoreCase("Y");
    }

    private String readNonEmptyString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String value = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(value);
                if (number < min || number > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
