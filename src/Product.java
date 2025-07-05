public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        this.quantity = quantity;
    }

    public boolean isOutOfStock() {
        return quantity == 0;
    }
    public void adjustQuantity(int amount) {
        int newQuantity = this.quantity + amount;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Cannot reduce quantity below 0 for the following product: " + name);
        }

        this.quantity = newQuantity;
    }

}
