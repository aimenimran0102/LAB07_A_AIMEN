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
