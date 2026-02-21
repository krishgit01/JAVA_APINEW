package designpatterns.strategy;

// Concrete Strategy
// Implements specific algorithm for credit card payment
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public  CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " using credit card with number: " + cardNumber);
    }

}
