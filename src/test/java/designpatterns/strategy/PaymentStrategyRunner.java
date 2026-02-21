package designpatterns.strategy;

public class PaymentStrategyRunner {
    public static void main(String[] args) {
        // Choose strategy at runtime
       PaymentStrategy creditCardPayment = new CreditCardPayment("1234-5678-9012-3456");
       PaymentContext context = new PaymentContext(creditCardPayment);
       context.executePayment(100);

        // Change strategy dynamically
        PaymentStrategy upiPayment = new UPIPayment("user@bank");
        context = new PaymentContext(upiPayment);
        context.executePayment(200);


    }
}
