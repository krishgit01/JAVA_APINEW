package designpatterns.factory;

public class FactoryRunner {
    public static void main(String[] args) {
        PaymentStrategy paymentStrategy1 = PaymentFactory.getPaymentMethod("card");
        paymentStrategy1.pay(100);

        PaymentStrategy paymentStrategy2 = PaymentFactory.getPaymentMethod("upi");
        paymentStrategy2.pay(200);

        //using lambda factory
        PaymentStrategy paymentStrategy3 =PaymentFactoryUsingLambda.getPaymentMethod("card");
        paymentStrategy3.pay(300);
    }
}

/*
The client does not care about:
Class names
Constructors
Object creation logic
Factory handles it.

🧠 Why Factory Pattern Is Powerful
 | Without Factory               | With Factory                |
| ----------------------------- | --------------------------- |
| Object creation scattered     | Centralized creation        |
| Tight coupling                | Loose coupling              |
| Hard to change logic          | Easy to modify              |
| Client knows concrete classes | Client depends on interface |

🎯 Factory Pattern Follows
✅ Encapsulation
✅ Single Responsibility Principle
✅ Open/Closed Principle
✅ Loose Coupling

 */