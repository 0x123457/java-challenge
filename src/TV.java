public class TV extends Product implements ShippableProduct{
    private double weight;
    @Override
    public double getWeight() {
        if(weight < 0)
            throw new IllegalArgumentException("Weight cannot be negative");
        return weight;
    }
    public TV(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        if (weight < 0) {
            throw new IllegalArgumentException("Invalid weight!");
        }
        this.weight = weight;
    }
}
