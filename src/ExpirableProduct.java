import java.time.LocalDate;
public class ExpirableProduct extends Product{
    private LocalDate expiryDate;
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}
