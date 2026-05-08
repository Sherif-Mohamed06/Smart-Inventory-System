The Smart Inventory Management System is a Java-based console application that enables warehouse staff to manage product inventory using text menus and keyboard input. The system uses in-memory Java collections with no persistent storage - all data is stored in RAM and is lost when the JVM terminates.

This project was developed as part of SCS253 - Software Process & Quality Management course, following the Waterfall methodology with complete SRS and SDD documentation.

Team Members:
Omar Mohamed Elsayed Ibrahim	20246076
Mahmoud Ayman AboBakr	20246100
Aly Adel Maamoun	20246072
Sheriff Mohamed	20246062
Aly Ahmed	20246069
Mahmoud Hassanein	20246153
Seif Sobhy	20246144


Features:
Core Functionality:
Add Product - Create new inventory items with SKU, name, quantity, and threshold

View All Products - Display all products in a formatted table

Update Product - Modify quantity and/or threshold with validation

Delete Product - Remove products with confirmation prompt

Low-Stock Detection - Automatic identification of products below threshold

Generate Reports - Full inventory and low-stock reports with statistics

Technical Features
Input Validation - Comprehensive validation for all user inputs

Error Handling - Graceful handling of invalid inputs and exceptions

Modular Design - Clean separation of concerns (UI, Service, Model, Utility)

Formatted Output - Column-aligned tables using formatted strings

No External Dependencies - Pure Java Standard Edition

Architecture
The system follows a Layered Architecture with three tiers:

text
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│                      Main.java                           │
│              (Menu display, user input handling)         │
└─────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                         │
│   InventoryManager.java    ReportGenerator.java         │
│      (CRUD operations)        (Report formatting)        │
└─────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────┐
│                     MODEL LAYER                          │
│                      Product.java                        │
│              (Data encapsulation, business logic)        │
└─────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────┐
│                    UTILITY LAYER                         │
│                   InputValidator.java                    │
│                 (Centralized validation)                 │
└─────────────────────────────────────────────────────────┘
Package Structure
text
com.inventory/
├── Main.java                      # Entry point & UI controller
├── model/
│   └── Product.java               # Product POJO with business logic
├── service/
│   ├── InventoryManager.java      # CRUD operations on inventory
│   └── ReportGenerator.java       # Formatted console reports
└── util/
    └── InputValidator.java        # Centralized validation rules
Data Model
Product Entity
Field	Type	Constraints	Description
sku	String	Not null, not blank, unique	Stock Keeping Unit (unique identifier)
name	String	Not null, not blank	Human-readable product name
quantity	int	≥ 0	Current stock quantity
threshold	int	≥ 1	Low-stock alert threshold
Derived Attribute
isLowStock() = quantity <= threshold (computed on the fly)

Getting Started
Prerequisites
Java JDK 11 or higher

Command line / Terminal

Installation
Clone the repository

bash
git clone https://github.com/Sherif-Mohamed06/Smart-Snventory-System.git
cd smart-inventory-system
Compile the source code


# On Windows
javac com\inventory\*.java com\inventory\model\*.java com\inventory\service\*.java com\inventory\util\*.java
Run the application

bash
# On Linux/Mac
java com.inventory.Main

# On Windows
java com.inventory.Main
Usage Guide
Main Menu
text
================================================================
                    SMART INVENTORY SYSTEM
================================================================
    1. Add Product
    2. View All Products
    3. Update Product
    4. Delete Product
    5. View Low-Stock Products
    6. Generate Report
    7. Exit
================================================================
Enter your choice: _
Sample Operations
Adding a Product
text
--- ADD NEW PRODUCT ---
Enter SKU (unique identifier): PRD-001
Enter Product Name: Office Chair
Enter Quantity: 25
Enter Low-Stock Threshold: 5

Product added successfully!
  SKU: PRD-001
  Name: Office Chair
  Quantity: 25
  Threshold: 5
View All Products
text
--- ALL PRODUCTS ---

#   SKU        Name                 Qty Threshold Status    
----------------------------------------------------------------------
1   PRD-001    Office Chair          25        5 OK        
2   PRD-002    Desk Lamp              3        5 LOW STOCK 
3   PRD-003    Monitor                8        4 OK        
4   PRD-004    Keyboard               2        3 LOW STOCK 
5   PRD-005    Mouse Pad              0        5 LOW STOCK 
----------------------------------------------------------------------
Total products: 5
Generating Report
text
================================================================
         Smart Inventory Management System
         FULL INVENTORY REPORT
         Generated: 2026-05-08 15:30:45
         Total products in system: 5
================================================================
#   SKU        Name                 Qty Threshold Status    
----------------------------------------------------------------
1   PRD-001    Office Chair          25        5 OK        
2   PRD-002    Desk Lamp              3        5 LOW STOCK 
3   PRD-003    Monitor                8        4 OK        
4   PRD-004    Keyboard               2        3 LOW STOCK 
5   PRD-005    Mouse Pad              0        5 LOW STOCK 
----------------------------------------------------------------
         Products shown: 5 | Total qty: 38 | Low-stock: 3
================================================================
Functional Requirements Traceability
FR ID	Requirement	Implemented In
FR-01	Welcome message on startup	Main.main()
FR-02	Main menu with 7 options	Main.displayMenu()
FR-03	Return to menu after operations	Main.main() while loop
FR-04	Scanner for menu input	Main.getIntInput()
FR-05	Handle InputMismatchException	Main.getIntInput()
FR-06	Error on out-of-range menu choice	Main.main() default case
FR-07	Prompt for product details	Main.addProduct()
FR-08	SKU not empty	InputValidator.isValidSku()
FR-09	Prevent duplicate SKU	InventoryManager.addProduct()
FR-10	Quantity ≥ 0	InputValidator.isValidQuantity()
FR-11	Threshold ≥ 1	InputValidator.isValidThreshold()
FR-12	Create and add Product object	InventoryManager.addProduct()
FR-13	Confirmation after add	Main.addProduct()
FR-14	Display all products	Main.viewAllProducts()
FR-15	Table columns	Main.viewAllProducts()
FR-16	Status logic	Product.isLowStock()
FR-17	Sequential row numbers	Main.viewAllProducts()
FR-18	Empty inventory message	Main.viewAllProducts()
FR-19	Insertion order preserved	InventoryManager.getAllProducts()
FR-20-26	Update product	Main.updateProduct()
FR-27-32	Delete product	Main.deleteProduct()
FR-33-36	Low-stock filter	InventoryManager.getLowStockProducts()
FR-37-41	Generate reports	ReportGenerator.printReport()
FR-42-44	Exit confirmation	Main.confirmExit()
Sample Test Data
Use these products for testing the application:

SKU	Name	Quantity	Threshold	Expected Status
PRD-001	Office Chair	25	5	OK
PRD-002	Desk Lamp	3	5	LOW STOCK
PRD-003	Monitor	8	4	OK
PRD-004	Keyboard	2	3	LOW STOCK
PRD-005	Mouse Pad	0	5	LOW STOCK
Technical Constraints
Allowed Packages
java.util.* - Collections, Scanner

java.time.* - Date/time for reports

java.lang.* - Core language features

Prohibited Features (Educational Constraints)
No external databases (JDBC, JPA, Hibernate, SQLite)

No file I/O persistence (FileInputStream, FileOutputStream, Serialization)

No GUI (Swing, JavaFX, AWT)

No network/socket programming

No external libraries (JSON, XML, Apache Commons)

No Spring Framework or ORM tools

Design Constraints
In-memory storage only - Data lost when JVM exits

Maximum capacity - 100 products (practical limit)

Single user - No threading/concurrency required

Console width - 80+ characters recommended

Running Tests
The application includes built-in input validation. To test specific scenarios:

Negative Testing
Test	Expected Behavior
Enter "abc" for menu choice	"Invalid input, please enter a number"
Enter "9" for menu choice	"Invalid option. Please enter 1-7"
Add duplicate SKU	"SKU already exists"
Add negative quantity	"Quantity cannot be negative"
Add threshold of 0	"Threshold must be at least 1"
Update with negative quantity	"Quantity cannot be negative"
Delete non-existent SKU	"Product not found"
Positive Testing
Test	Expected Behavior
Add new product	Confirmation message appears
View products	Formatted table displays
Update quantity	New value persists
Delete with 'Y' confirmation	Product removed
Delete with 'N' confirmation	Product remains
Low-stock view	Only filtered products shown
Generate report	Statistics calculated correctly
Project Files
text
smart-inventory-system/
├── com/
│   └── inventory/
│       ├── Main.java                 # Entry point (150+ lines)
│       ├── model/
│       │   └── Product.java          # Product entity (70+ lines)
│       ├── service/
│       │   ├── InventoryManager.java # CRUD operations (90+ lines)
│       │   └── ReportGenerator.java  # Report formatting (100+ lines)
│       └── util/
│           └── InputValidator.java   # Validation rules (60+ lines)
├── docs/
│   ├── SRS_final.docx                # Software Requirements Spec
│   └── SDD_SmartInventory.docx       # Software Design Document
└── README.md                         # This file
