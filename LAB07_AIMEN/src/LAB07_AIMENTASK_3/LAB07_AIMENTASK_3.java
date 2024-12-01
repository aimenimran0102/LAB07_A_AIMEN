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


