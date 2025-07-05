import java.time.LocalDate;

public class Cheese extends ExpirableProduct implements ShippableProduct{
    double weight;
    public Cheese(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        if (weight < 0) {
            throw new IllegalArgumentException("Invalid weight!");
        }
        this.weight = weight;
    }
    @Override
    public double getWeight() {
        return this.weight;
    }
}
