import java.time.LocalDate;

public class Biscuits extends ExpirableProduct implements ShippableProduct{
   double weight;
    public Biscuits(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        if(weight < 0){
            throw new IllegalArgumentException("weight cannot be negative");
        }
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}
