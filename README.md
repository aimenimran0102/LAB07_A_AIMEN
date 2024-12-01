# LAB07_A_AIMEN
package LAB07_AIMENTASK_3;
import java.util.HashMap;
import java.util.Map;
class Product {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(String id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, stockQuantity=%d}", id, name, price, stockQuantity);
    }
}
public class LAB07_AIMENTASK_3 {
	    private static final double TAX_RATE = 0.08;
	    private static final double DISCOUNT_RATE = 0.10;

	    private Map<String, Product> productStock;

	    public LAB07_AIMENTASK_3 () {
	        productStock = new HashMap<>();
	    }

	    public void loadProducts(Map<String, Product> products) {
	        productStock.putAll(products);
	    }

	    public void updateStock(String productId, int quantity) {
	        Product product = productStock.get(productId);
	        if (product != null) {
	            product.setStockQuantity(product.getStockQuantity() + quantity);
	        }
	    }

	    public double calculateTotalPrice(String productId, int quantity) {
	        Product product = productStock.get(productId);
	        if (product == null) {
	            return 0;
	        }
	        double price = product.getPrice() * quantity;
	        return applyDiscountAndTax(price);
	    }

	    private double applyDiscountAndTax(double price) {
	        double discountedPrice = price - (price * DISCOUNT_RATE);
	        return discountedPrice + (discountedPrice * TAX_RATE);
	    }

	    public void displayAvailableProducts() {
	        productStock.values().stream()
	            .filter(product -> product.getStockQuantity() > 0)
	            .forEach(product -> System.out.println(product));
	    }
public static void main(String[] args) {
	LAB07_AIMENTASK_3  manager = new LAB07_AIMENTASK_3 ();
    // Sample product loading
    Map<String, Product> initialProducts = new HashMap<>();
    initialProducts.put("001", new Product("001", "Lipstick", 19.99, 200));
    initialProducts.put("002", new Product("002", "Foundation", 29.99, 500));
    manager.loadProducts(initialProducts);

    // Updating stock
    manager.updateStock("001", 20);
    
    // Display available products
    manager.displayAvailableProducts();
    
    // Calculate total price for an order
    double totalPrice = manager.calculateTotalPrice("002", 2);
    System.out.printf("Total price for 2 Foundations: $%.2f%n", totalPrice);
}
}


package LAB07_AIMENTASK_4;
import java.util.ArrayList;
import java.util.List;

public class LAB07_AIMENTASK_4 {
    public static class CosmeticsProductManager {
        private static final List<Product> productList = new ArrayList<>();
        private static volatile boolean running = true; // Flag to control thread execution

        private static void loadInitialProducts() {
            productList.add(new Product("Lipstick", 19.99, 100));
            productList.add(new Product("\nFoundation", 29.99, 50));
            productList.add(new Product("\nMascara", 15.99, 75));
            productList.add(new Product("\nBlush", 22.99, 30));
        }

        // Task for performing calculations
        static class CalculationTask implements Runnable {
            @Override
            public void run() {
                System.out.println("\nCalculation thread started.");
                try {
                    // Simulating some calculation work with sleep
                    for (Product product : productList) {
                        double totalValue = product.getPrice() * product.getStockQuantity();
                        System.out.printf("\nTotal value of %s: $%.2f%n", product.getName(), totalValue);
                        // Simulate time taken for calculations
                        Thread.sleep(500); // Sleep for 500 ms
                    }
                } catch (InterruptedException e) {
                    System.out.println("Calculation task interrupted: " + e.getMessage());
                }
                System.out.println("Calculation thread finished.");
            }
        }

        // Task for logging product information
        static class LoggingTask implements Runnable {
            @Override
            public void run() {
                System.out.println("\nLogging thread started.");
                while (running) {
                    try {
                        // Log current product information every second
                        System.out.println("\nCurrent products: " + productList);
                        Thread.sleep(1000); // Sleep for 1 second
                    } catch (InterruptedException e) {
                        System.out.println("Logging task interrupted: " + e.getMessage());
                    }
                }
                System.out.println("Logging thread stopping.");
            }
        }

        // Product class representing each cosmetic product
        static class Product {
            private final String name;
            private final double price;
            private final int stockQuantity;

            public Product(String name, double price, int stockQuantity) {
                this.name = name;
                this.price = price;
                this.stockQuantity = stockQuantity;
            }

            public String getName() {
                return name;
            }

            public double getPrice() {
                return price;
            }

            public int getStockQuantity() {
                return stockQuantity;
            }

            @Override
            public String toString() {
                return String.format("%s (Price: $%.2f, Stock: %d)", name, price, stockQuantity);
            }
        }

        public static void main(String[] args) {
            // Load initial products into the list
            loadInitialProducts();

            // Display initial product details
            System.out.println("Initial Products:");
            System.out.println(productList);

            // Create and start the logging thread first
            Thread loggingThread = new Thread(new LoggingTask());
            loggingThread.start();

            // Allow some time for logging before calculations
            try {
                Thread.sleep(2000); // Sleep for 2 seconds to gather initial logs
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted: " + e.getMessage());
            }

            // Create and start the calculation thread
            Thread calculationThread = new Thread(new CalculationTask());
            calculationThread.start();

            try {
                // Wait for the calculation thread to finish before stopping the logging thread
                calculationThread.join();
                // After calculations, we can stop the logging thread
                running = false; // Stop condition for the logging thread
                loggingThread.join(); // Wait for logging thread to finish
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }

            System.out.println("\nAll tasks completed successfully.");
        }
    }
}


package LAB07_AIMENTASK_5;
import java.util.LinkedList;
import java.util.Queue;
public class LAB07_AIMENTASK_5 {
	 private static final int MAX_STOCK = 5; // Maximum number of products in stock
	    private final Queue<Product> stockBuffer = new LinkedList<>();
	    private static final String[] PRODUCT_NAMES = {
	        "Lipstick", "Foundation", "Mascara", "Blush", "Nail Polish",
	        "Eye Shadow", "Concealer", "Bronzer", "Setting Spray", "Highlighter"
	    };

	    public static void main(String[] args) {
	    	LAB07_AIMENTASK_5  manager = new LAB07_AIMENTASK_5 ();

	        Thread producerThread = new Thread(new Producer(manager));
	        Thread consumerThread = new Thread(new Consumer(manager));

	        producerThread.start();
	        consumerThread.start();

	        try {
	            producerThread.join();
	            consumerThread.join();
	        } catch (InterruptedException e) {
	            System.out.println("Thread interrupted: " + e.getMessage());
	        }

	        System.out.println("All tasks completed.");
	    }

	    // Method to add product to stock
	    public synchronized void produce(Product product) throws InterruptedException {
	        while (stockBuffer.size() == MAX_STOCK) {
	            wait(); // Wait until space is available in the buffer
	        }
	        stockBuffer.add(product);
	        System.out.println("Produced: " + product);
	        notify(); // Notify a waiting consumer
	    }

	    // Method to remove product from stock
	    public synchronized Product consume() throws InterruptedException {
	        while (stockBuffer.isEmpty()) {
	            wait(); // Wait until a product is available
	        }
	        Product product = stockBuffer.poll();
	        System.out.println("Consumed: " + product);
	        notify(); // Notify a waiting producer
	        return product;
	    }

	    // Producer class to produce products
	    static class Producer implements Runnable {
	        private final LAB07_AIMENTASK_5  manager;

	        public Producer(LAB07_AIMENTASK_5  manager) {
	            this.manager = manager;
	        }

	        @Override
	        public void run() {
	            try {
	                // Produce a fixed number of products with names from PRODUCT_NAMES
	                for (int i = 0; i < PRODUCT_NAMES.length; i++) {
	                    String productName = PRODUCT_NAMES[i];
	                    Product product = new Product(productName, 19.99 + i); // Increment price
	                    manager.produce(product);
	                    Thread.sleep(500); // Simulate time taken to produce a product
	                }
	            } catch (InterruptedException e) {
	                System.out.println("Producer interrupted: " + e.getMessage());
	            }
	        }
	    }

	    // Consumer class to consume products
	    static class Consumer implements Runnable {
	        private final LAB07_AIMENTASK_5  manager;

	        public Consumer(LAB07_AIMENTASK_5  manager) {
	            this.manager = manager;
	        }

	        @Override
	        public void run() {
	            try {
	                for (int i = 0; i < PRODUCT_NAMES.length; i++) {
	                    manager.consume();
	                    Thread.sleep(1000); // Simulate time taken to consume a product
	                }
	            } catch (InterruptedException e) {
	                System.out.println("Consumer interrupted: " + e.getMessage());
	            }
	        }
	    }

	    // Product class representing each cosmetic product
	    static class Product {
	        private final String name;
	        private final double price;

	        public Product(String name, double price) {
	            this.name = name;
	            this.price = price;
	        }

	        @Override
	        public String toString() {
	            return String.format("%s (Price: $%.2f)", name, price);
	        }
	    }
	}

