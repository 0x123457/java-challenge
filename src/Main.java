import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final String CHEESE_NAME = "Cheese";
    static final double CHEESE_PRICE = 100;
    static final int CHEESE_QUANTITY = 5;
    static final LocalDate CHEESE_EXPIRY = LocalDate.of(2030, 2, 18);
    static final double CHEESE_WEIGHT = 0.4;

    static final String BISCUITS_NAME = "Biscuits";
    static final double BISCUITS_PRICE = 150;
    static final int BISCUITS_QUANTITY = 3;
    static final LocalDate BISCUITS_EXPIRY = LocalDate.of(2030, 2, 18);
    static final double BISCUITS_WEIGHT = 0.7;

    static final String TV_NAME = "TV";
    static final double TV_PRICE = 200;
    static final int TV_QUANTITY = 10;
    static final double TV_WEIGHT = 10;

    static final String MOBILE_NAME = "Mobile";
    static final double MOBILE_PRICE = 500;
    static final int MOBILE_QUANTITY = 1;

    static final String SCRATCHCARD_NAME = "Scratch Card";
    static final double SCRATCHCARD_PRICE = 50;
    static final int SCRATCHCARD_QUANTITY = 10;

    static final double CUSTOMER_BALANCE = 1000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Cheese cheese = new Cheese(CHEESE_NAME, CHEESE_PRICE, CHEESE_QUANTITY, CHEESE_EXPIRY, CHEESE_WEIGHT);
        Biscuits biscuits = new Biscuits(BISCUITS_NAME, BISCUITS_PRICE, BISCUITS_QUANTITY, BISCUITS_EXPIRY, BISCUITS_WEIGHT);
        TV tv = new TV(TV_NAME, TV_PRICE, TV_QUANTITY, TV_WEIGHT);
        ScratchCard card = new ScratchCard(SCRATCHCARD_NAME, SCRATCHCARD_PRICE, SCRATCHCARD_QUANTITY);
        Mobile mobile = new Mobile(MOBILE_NAME, MOBILE_PRICE, MOBILE_QUANTITY);

        Customer customer = new Customer(CUSTOMER_BALANCE);

        boolean flag = true;

        while (flag) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Add Cheese");
            System.out.println("2. Add Biscuits");
            System.out.println("3. Add TV");
            System.out.println("4. Add Mobile");
            System.out.println("5. Add Scratch Card");
            System.out.println("6. View Cart");
            System.out.println("7. Finish & Checkout");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addToCart(sc, customer, cheese);
                case 2 -> addToCart(sc, customer, biscuits);
                case 3 -> addToCart(sc, customer, tv);
                case 4 -> addToCart(sc, customer, mobile);
                case 5 -> addToCart(sc, customer, card);
                case 6 -> viewCart(customer);
                case 7 -> {
                    checkout(customer);
                    flag = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close(); // after the loop exits, we close the scanner object as we are done with the user input (and fortunately, the whole process as well. The program can exit in peace.)
    }

    private static void addToCart(Scanner sc, Customer customer, Product product) {
        System.out.print("Enter quantity to add for " + product.getName() + ": ");
        int qty = sc.nextInt();
        customer.getCart().addProduct(product, qty);
    }

    private static void viewCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n** Cart Contents **");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " @ " + item.getProduct().getPrice() + " = " + item.getSubtotal());
        }
        System.out.println("Subtotal: " + cart.getSubtotal());
    }

    public static void checkout(Customer customer) {
        Cart cart = customer.getCart();

        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty.");
            return;
        }

        List<CartItem> items = cart.getItems();
        List<ShippableProduct> shippables = new ArrayList<>();
        double totalShippingWeight = 0;
        double subtotal = 0;

        for (CartItem item : items) {
            Product p = item.getProduct();

            if (p instanceof ExpirableProduct ex && ex.isExpired()) {
                System.out.println("Error: Product " + p.getName() + " is expired.");
                return;
            }

            if (p.isOutOfStock() || item.getQuantity() > p.getQuantity()) {
                System.out.println("Error: Product " + p.getName() + " is out of stock.");
                return;
            }

            if (p instanceof ShippableProduct s) {
                totalShippingWeight += s.getWeight() * item.getQuantity();
                shippables.add(s);
            }

            subtotal += item.getSubtotal();
        }

        double shippingFee = totalShippingWeight > 0 ? 30 : 0;
        double totalAmount = subtotal + shippingFee;

        if (totalAmount > customer.getBalance()) {
            System.out.println("Error: insufficient balance. Required: " + totalAmount + ", Available: " + customer.getBalance());
            return;
        }

        for (CartItem item : items) {
            item.getProduct().adjustQuantity(-item.getQuantity());
        }

        customer.deductFromBalance(totalAmount);

        if (shippingFee > 0) {
            System.out.println("** Shipment notice **");
            for (CartItem item : items) {
                if (item.getProduct() instanceof ShippableProduct sp)
                    System.out.printf("%dx %s %.0fg\n", item.getQuantity(), sp.getName(), sp.getWeight() * 1000);

            }
            System.out.printf("Total package weight %.1fkg\n", totalShippingWeight);
        }

        System.out.println("** Checkout receipt **");
        for (CartItem item : items) {
            System.out.printf("%dx %s %.0f\n", item.getQuantity(), item.getProduct().getName(), item.getSubtotal());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f\n", subtotal);
        System.out.printf("Shipping %.0f\n", shippingFee);
        System.out.printf("Amount %.0f\n", totalAmount);
        System.out.printf("Remaining Balance: %.0f\n", customer.getBalance());
    }
}
