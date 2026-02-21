package designpatterns.strategy;

// Context class
// This class does NOT know how payment works internally
// It just uses the strategy provided to it
public class PaymentContext {
    private final PaymentStrategy paymentStrategy;

    // Strategy is injected (dependency injection)
    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Delegates payment to selected strategy
    public void executePayment(int amount) {
        paymentStrategy.pay(amount);
    }

//    public void changeStrategy(PaymentStrategy newStrategy) {
//        // In a real implementation, you might want to allow changing the strategy at runtime
//        // This is just a placeholder to show that the context can support dynamic strategy changes
//        this.paymentStrategy = newStrategy;
//    }

    //paymentStrategy is declared final will not allow changing the strategy at runtime,
    // if you want to allow changing the strategy at runtime, you can remove the final keyword and implement the changeStrategy method as shown above.
}
