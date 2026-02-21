package designpatterns.strategy;

// Strategy Interface
// Defines common method for all payment types
public interface PaymentStrategy {
    // Every payment method must implement this
    void pay(int amount);
}
