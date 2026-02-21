package designpatterns.strategy;

/**
 * Demonstrates the Strategy design pattern using Java lambdas.
 *
 * - PaymentStrategy is a functional interface that encapsulates a payment algorithm.
 * - PaymentContext holds a PaymentStrategy and delegates payment execution to it.
 *
 * This runner creates two different strategies (credit card and UPI) using lambdas
 * and executes them via the context. No behavior changes are made—only comments
 * were added for clarity.
 */
public class PaymentStrategyLambdaRunner {
    public static void main(String[] args) {

        // Lambda implementing PaymentStrategy: prints a credit-card payment message.
        PaymentStrategy creditCardPayment = amount -> System.out.println("Paying " + amount + " using Credit Card");

        // Create a context with the credit card strategy and execute a payment of 100.
        // PaymentContext is responsible for invoking the strategy's payment behavior.
        PaymentContext context = new PaymentContext(creditCardPayment);
        context.executePayment(100);

        // Another lambda implementing PaymentStrategy for UPI payments.
        PaymentStrategy upiPayment = amount -> System.out.println("Paying " + amount + " using UPI");

        // Swap the strategy in the context by creating a new context with the UPI strategy
        // (alternatively, PaymentContext could support a setter to change strategy at runtime).
        context = new PaymentContext(upiPayment);
        context.executePayment(200);
    }
}
