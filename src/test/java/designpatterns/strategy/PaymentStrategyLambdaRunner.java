package designpatterns.strategy;

/**
 * Demonstrates the Strategy design pattern using Java lambdas.
 * <p>
 * - PaymentStrategy is a functional interface that encapsulates a payment algorithm.
 * - PaymentContext holds a PaymentStrategy and delegates payment execution to it.
 * <p>
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


/*
| Traditional Strategy   | Lambda Strategy      |
| ---------------------- | -------------------- |
| Requires classes       | No extra classes     |
| More boilerplate       | Minimal code         |
| Good for complex logic | Good for small logic |
| Classic OOP            | Functional style     |

🎯 When Lambda Strategy Is Best
Use Lambda Strategy when:
logic is small
No complex state required
You want lightweight behavior injection
You're building DSL-like frameworks

⚠ When NOT to Use Lambda
Don’t use lambda if:
Strategy has complex logic
Strategy has multiple methods
Strategy needs heavy state
Strategy is reused in many places
Then normal class is better.
 */