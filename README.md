# Small Retail Store Management System

A Java-based retail store management system for managing multiple retail stores, customer profiles, and product inventory. This system handles orders for Apple and Microsoft products across multiple store locations.

## Features

- **Store Management**: Manage multiple retail stores (up to 3 per manager)
- **Customer Profiles**: Track customer information and order history
- **Inventory Management**: Monitor and update product stock levels
- **Order Processing**: Submit and track customer orders with stock validation
- **Multi-Store Support**: Find products across different store locations
- **Exception Handling**: Robust error handling for common scenarios

## Supported Products

The system supports six product types:

### Apple Products
1. iPad Mini
2. iPad Pro 12.9
3. Apple Magic Keyboard

### Microsoft Products
4. Surface Pro 9
5. Surface Laptop 5
6. Surface Pro Keyboard with Slim Pen 2

## Project Structure

```
.
├── Small_Retail_Store/
│   ├── src/
│   │   ├── model/
│   │   │   ├── CustomerProfile.java          # Customer profile and order management
│   │   │   ├── RetailStore.java              # Store operations and inventory
│   │   │   ├── StoreManager.java             # Manager for multiple stores (Singleton)
│   │   │   ├── InsufficientStockException.java
│   │   │   ├── InvalidProductKindException.java
│   │   │   └── TooManyStoresException.java
│   │   └── junit_tests/
│   │       └── StarterTests.java             # JUnit test suite
│   └── bin/                                   # Compiled class files
└── README.md
```

## Core Components

### CustomerProfile
Manages customer information and orders:
- Customer name and order limits
- Order history tracking
- Product validation
- Brand identification (Apple/Microsoft)

### RetailStore
Handles store operations:
- Stock management for all product types
- Order submission and processing
- Stock availability queries
- Cross-store product searches

### StoreManager
Singleton pattern implementation for managing multiple stores:
- Manages up to 3 retail stores
- Store assignment and tracking
- Centralized store management

## Exception Handling

The system includes three custom exceptions:

- **InsufficientStockException**: Thrown when order quantity exceeds available stock
- **InvalidProductKindException**: Thrown when an invalid product name is used
- **TooManyStoresException**: Thrown when attempting to assign more than 3 stores to a manager

## Building and Running

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- JUnit 4 for running tests

### Compilation
```bash
# Compile the source files
javac -cp Small_Retail_Store/src -d Small_Retail_Store/bin Small_Retail_Store/src/model/*.java

# Compile with JUnit tests (adjust paths to your JUnit installation, e.g., junit-4.13.jar)
# On Unix/Linux/Mac:
javac -cp Small_Retail_Store/src:path/to/junit-4.13.jar -d Small_Retail_Store/bin Small_Retail_Store/src/model/*.java Small_Retail_Store/src/junit_tests/*.java
# On Windows:
javac -cp Small_Retail_Store/src;path/to/junit-4.13.jar -d Small_Retail_Store/bin Small_Retail_Store/src/model/*.java Small_Retail_Store/src/junit_tests/*.java
```

### Running Tests
```bash
# Run JUnit tests (adjust paths to your JUnit installation, e.g., junit-4.13.jar and hamcrest-core-1.3.jar)
# On Unix/Linux/Mac:
java -cp Small_Retail_Store/bin:path/to/junit-4.13.jar:path/to/hamcrest-core-1.3.jar org.junit.runner.JUnitCore junit_tests.StarterTests
# On Windows:
java -cp Small_Retail_Store/bin;path/to/junit-4.13.jar;path/to/hamcrest-core-1.3.jar org.junit.runner.JUnitCore junit_tests.StarterTests
```

## Usage Example

```java
// Create a store manager (Singleton)
StoreManager manager = StoreManager.getInstance();
manager.setName("John Doe");

// Create retail stores
RetailStore store1 = new RetailStore("Downtown Store");
RetailStore store2 = new RetailStore("Uptown Store");

// Assign stores to manager
try {
    store1.setManager(manager);
    store2.setManager(manager);
} catch (TooManyStoresException e) {
    System.out.println("Cannot assign store: " + e.getMessage());
}

// Add stock to a store
store1.addStock("iPad Mini", 50);
store1.addStock("Surface Pro 9", 30);

// Create a customer profile
CustomerProfile customer = new CustomerProfile("Alice", 10);

// Add an order to customer profile
try {
    customer.addOrder("iPad Mini", 2, "Downtown Store", "2024-01-15");
} catch (InvalidProductKindException e) {
    System.out.println("Invalid product: " + e.getMessage());
}

// Submit order to store
try {
    store1.submitOrder(customer, "iPad Mini", 2, "2024-01-15");
    System.out.println(customer.getStatusofLastOrder());
} catch (InsufficientStockException e) {
    System.out.println("Order failed: " + e.getMessage());
}

// Find product in other stores
String[] otherStores = store1.findProductsInOtherStores("iPad Mini");
```

## Design Patterns

- **Singleton Pattern**: StoreManager uses the Singleton pattern to ensure only one manager instance exists
- **Exception Handling**: Custom exceptions for domain-specific error cases

## Contributing

This appears to be an educational project. If you'd like to contribute:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

No license information provided. Please contact the repository owner for licensing details.