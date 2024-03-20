package design.patterns;

import design.patterns.order.Order;
import design.patterns.strategies.PayByCreditCard;
import design.patterns.strategies.PayByPayPal;
import design.patterns.strategies.PayStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

// Client
public class Main {

    private static Map<Integer, Integer> priceOnProducts = new HashMap<>();

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static Order order = new Order();

    private static PayStrategy strategy;

    static {
        priceOnProducts.put(1, 2200);
        priceOnProducts.put(2, 1850);
        priceOnProducts.put(3, 1100);
        priceOnProducts.put(4, 890);
    }

    public static void main(String[] args) throws IOException {
        while (!order.isClosed()) {
            shop();

            selectPaymentMethod();

            System.out.println("Pay " + order.getTotalCost() + " units or Continue shopping? P/C: ");
            String proceed = reader.readLine();
            if (proceed.equalsIgnoreCase("P")) {
                pay();
            }
        }
    }

    private static void pay() {
        // finally, strategy handles the payment
        if (strategy.pay(order.getTotalCost())) {
            System.out.println("Payment has been successful.");
        } else {
            System.out.println("FAIL! Please, check your data.");
        }

        order.setClosed();
    }

    private static void selectPaymentMethod() throws IOException {
        if (strategy == null) {
            System.out.println("""
                    Please, select a payment method:
                    1 - PayPal
                    2- Credit Card
                    """);
            String paymentMethod = reader.readLine();

            // Client creates different strategies based on input from user,
            // app configuration, etc
            if (paymentMethod.equals("1")) {
                strategy = new PayByPayPal();
            } else {
                strategy = new PayByCreditCard();
            }
        }

        // order object delegates gathering payment data to strategy object,
        // since only strategies know what data they need to process a payment
        order.processOrder(strategy);
    }

    private static void shop() throws IOException {
        int cost;

        String continueChoice;
        do {
            System.out.println("""
                    Please, select a product:
                    1 - Mother board
                    2 - CPU
                    3 - HDD
                    4 - Memory
                    """);
            int choice = Integer.parseInt(reader.readLine());
            cost = priceOnProducts.get(choice);
            System.out.println("Count: ");
            int count = Integer.parseInt(reader.readLine());
            order.setTotalCost(cost * count);
            System.out.println("Do you wish to continue selecting products? Y/N: ");
            continueChoice = reader.readLine();
        } while (continueChoice.equalsIgnoreCase("Y"));
    }
}