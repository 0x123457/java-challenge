import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be at least 1");
            return;
        }
        if (quantity > product.getQuantity()) {
            System.out.println("Quantity exceeds available stock for: " + product.getName());
            return;
        }

        items.add(new CartItem(product, quantity));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }
    public List<CartItem> getItems() {
        return items;
    }

}
