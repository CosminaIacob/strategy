package design.patterns.strategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Concrete strategy
public class PayByCreditCard implements PayStrategy {

    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private CreditCard card;

    // Collect credit card data
    @Override
    public void collectPaymentDetails() {
        try {
            System.out.println("Enter the card number: ");
            String number = READER.readLine();
            System.out.println("Enter the card expiration data 'mm/yy': ");
            String date = READER.readLine();
            System.out.println("Enter the CVV code: ");
            String cvv = READER.readLine();
            card = new CreditCard(number, date, cvv);

            // validate credit card number...
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // After card validation we charge customer's credit card
    @Override
    public boolean pay(int paymentAmount) {
        if (cardIsPresent()) {
            System.out.println("Paying " + paymentAmount + " using Credit Card.");
            card.setAmount(card.getAmount() - paymentAmount);
            return true;
        }
        return false;
    }

    private boolean cardIsPresent() {
        return card != null;
    }

}
