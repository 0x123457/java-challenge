public class Customer {
    private double balance;
    private Cart cart;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public Customer(double balance) {
        setBalance(balance);
        this.cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void deductFromBalance(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
    }
}
