package com.inventory;

import com.inventory.model.Product;
import com.inventory.service.InventoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InventoryManager Service Tests")
class InventoryManagerTest {
    private InventoryManager inventory;
    @BeforeEach
    void setUp() {
        inventory = new InventoryManager();
    }

    @Test
    @DisplayName("Adding a unique product should return true")
    void testAddUniqueProduct() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        boolean result = inventory.addProduct(product);
        
        assertTrue(result, "Adding a unique product should return true");
        assertEquals(1, inventory.getAllProducts().size(), "Inventory should contain 1 product");
    }

    @Test
    @DisplayName("Adding a duplicate product ID should return false")
    void testAddDuplicateProduct() {
        Product product1 = new Product("PRD-001", "Office Chair", 50, 10);
        Product product2 = new Product("PRD-001", "Desk Lamp", 30, 5);
        
        assertTrue(inventory.addProduct(product1), "First product should be added");
        assertFalse(inventory.addProduct(product2), "Duplicate product ID should not be added");
        assertEquals(1, inventory.getAllProducts().size(), "Inventory should still contain only 1 product");
    }

    @Test
    @DisplayName("Adding duplicate ID with different case should return false")
    void testAddDuplicateProductCaseInsensitive() {
        Product product1 = new Product("PRD-001", "Office Chair", 50, 10);
        Product product2 = new Product("prd-001", "Desk Lamp", 30, 5);
        
        assertTrue(inventory.addProduct(product1), "First product should be added");
        assertFalse(inventory.addProduct(product2), "Duplicate product ID (case-insensitive) should not be added");
        assertEquals(1, inventory.getAllProducts().size(), "Inventory should contain only 1 product");
    }

    @Test
    @DisplayName("Adding null product should return false")
    void testAddNullProduct() {
        boolean result = inventory.addProduct(null);
        assertFalse(result, "Adding null product should return false");
        assertEquals(0, inventory.getAllProducts().size(), "Inventory should be empty");
    }

    @Test
    @DisplayName("Finding product by exact ID should return the product")
    void testFindProductByExactId() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        Product found = inventory.findById("PRD-001");
        assertNotNull(found, "Product should be found with exact ID");
        assertEquals("PRD-001", found.getSku());
        assertEquals("Office Chair", found.getName());
    }

    @Test
    @DisplayName("Finding product by ID with different case should return the product")
    void testFindProductByIdCaseInsensitive() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        Product found = inventory.findById("prd-001");
        assertNotNull(found, "Product should be found with case-insensitive ID");
        assertEquals("PRD-001", found.getSku());
    }

    @Test
    @DisplayName("Finding non-existent product should return null")
    void testFindNonExistentProduct() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        Product found = inventory.findById("PRD-999");
        assertNull(found, "Non-existent product should return null");
    }

    @Test
    @DisplayName("Finding product with null ID should return null")
    void testFindProductWithNullId() {
        Product found = inventory.findById(null);
        assertNull(found, "Finding with null ID should return null");
    }

    @Test
    @DisplayName("Deleting existing product by ID should return true")
    void testDeleteExistingProduct() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        boolean result = inventory.deleteById("PRD-001");
        assertTrue(result, "Deleting existing product should return true");
        assertEquals(0, inventory.getAllProducts().size(), "Inventory should be empty after deletion");
    }

    @Test
    @DisplayName("Deleting product by ID with different case should return true")
    void testDeleteProductCaseInsensitive() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        boolean result = inventory.deleteById("prd-001");
        assertTrue(result, "Deleting product with case-insensitive ID should return true");
        assertEquals(0, inventory.getAllProducts().size(), "Inventory should be empty");
    }

    @Test
    @DisplayName("Deleting non-existent product should return false")
    void testDeleteNonExistentProduct() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        boolean result = inventory.deleteById("PRD-999");
        assertFalse(result, "Deleting non-existent product should return false");
        assertEquals(1, inventory.getAllProducts().size(), "Inventory should still contain 1 product");
    }

    @Test
    @DisplayName("Deleting with null ID should return false")
    void testDeleteWithNullId() {
        boolean result = inventory.deleteById(null);
        assertFalse(result, "Deleting with null ID should return false");
    }

    @Test
    @DisplayName("Getting all products should return unmodifiable list in insertion order")
    void testGetAllProducts() {
        Product product1 = new Product("PRD-001", "Office Chair", 50, 10);
        Product product2 = new Product("PRD-002", "Desk Lamp", 30, 5);
        Product product3 = new Product("PRD-003", "Monitor", 20, 8);
        
        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);
        
        List<Product> allProducts = inventory.getAllProducts();
        assertEquals(3, allProducts.size(), "Inventory should contain 3 products");
        assertEquals("PRD-001", allProducts.get(0).getSku(), "First product should be PRD-001");
        assertEquals("PRD-002", allProducts.get(1).getSku(), "Second product should be PRD-002");
        assertEquals("PRD-003", allProducts.get(2).getSku(), "Third product should be PRD-003");
    }

    @Test
    @DisplayName("Getting low-stock products when all are above threshold")
    void testGetLowStockProductsNone() {
        inventory.addProduct(new Product("PRD-001", "Office Chair", 50, 10));
        inventory.addProduct(new Product("PRD-002", "Desk Lamp", 30, 5));
        
        List<Product> lowStock = inventory.getLowStockProducts();
        assertEquals(0, lowStock.size(), "No products should be low on stock");
    }

    @Test
    @DisplayName("Getting low-stock products when some are below threshold")
    void testGetLowStockProductsSome() {
        inventory.addProduct(new Product("PRD-001", "Office Chair", 50, 10)); // OK
        inventory.addProduct(new Product("PRD-002", "Desk Lamp", 3, 5));      // LOW STOCK
        inventory.addProduct(new Product("PRD-003", "Monitor", 5, 5));        // LOW STOCK (at threshold)
        inventory.addProduct(new Product("PRD-004", "Keyboard", 20, 8));      // OK
        
        List<Product> lowStock = inventory.getLowStockProducts();
        assertEquals(2, lowStock.size(), "2 products should be low on stock");
        assertTrue(lowStock.stream().anyMatch(p -> p.getSku().equals("PRD-002")));
        assertTrue(lowStock.stream().anyMatch(p -> p.getSku().equals("PRD-003")));
    }

    @Test
    @DisplayName("Getting low-stock products when all are below threshold")
    void testGetLowStockProductsAll() {
        inventory.addProduct(new Product("PRD-001", "Office Chair", 5, 10));
        inventory.addProduct(new Product("PRD-002", "Desk Lamp", 2, 5));
        inventory.addProduct(new Product("PRD-003", "Monitor", 3, 8));
        
        List<Product> lowStock = inventory.getLowStockProducts();
        assertEquals(3, lowStock.size(), "All 3 products should be low on stock");
    }

    @Test
    @DisplayName("Getting total quantity when inventory is empty")
    void testGetTotalQuantityEmpty() {
        int total = inventory.getTotalQuantity();
        assertEquals(0, total, "Total quantity of empty inventory should be 0");
    }

    @Test
    @DisplayName("Getting total quantity with multiple products")
    void testGetTotalQuantityMultiple() {
        inventory.addProduct(new Product("PRD-001", "Office Chair", 50, 10));
        inventory.addProduct(new Product("PRD-002", "Desk Lamp", 30, 5));
        inventory.addProduct(new Product("PRD-003", "Monitor", 20, 8));
        
        int total = inventory.getTotalQuantity();
        assertEquals(100, total, "Total quantity should be 100 (50 + 30 + 20)");
    }

    @Test
    @DisplayName("Updating product quantity and threshold should work correctly")
    void testUpdateProduct() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        boolean result = inventory.updateProduct("PRD-001", 75, 15);
        
        assertTrue(result, "Update should return true");
        Product updated = inventory.findById("PRD-001");
        assertNotNull(updated);
        assertEquals(75, updated.getQuantity(), "Quantity should be updated to 75");
        assertEquals(15, updated.getThreshold(), "Threshold should be updated to 15");
    }

    @Test
    @DisplayName("Updating non-existent product should return false")
    void testUpdateNonExistentProduct() {
        boolean result = inventory.updateProduct("PRD-999", 100, 20);
        assertFalse(result, "Updating non-existent product should return false");
    }

    @Test
    @DisplayName("Updating product with case-insensitive ID should work")
    void testUpdateProductCaseInsensitive() {
        Product product = new Product("PRD-001", "Office Chair", 50, 10);
        inventory.addProduct(product);
        
        boolean result = inventory.updateProduct("prd-001", 75, 15);
        
        assertTrue(result, "Case-insensitive update should return true");
        Product updated = inventory.findById("PRD-001");
        assertEquals(75, updated.getQuantity());
    }
}
