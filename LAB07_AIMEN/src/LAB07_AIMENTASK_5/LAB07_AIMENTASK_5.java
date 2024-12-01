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

