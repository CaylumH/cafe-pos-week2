package com.cafepos.demo;

import java.util.Scanner;

import com.cafepos.common.Product;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.factory.ProductFactory;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.payment.WalletPayment;
import com.cafepos.pricing.DiscountPolicy;
import com.cafepos.pricing.FixedRateTaxPolicy;
import com.cafepos.pricing.LoyaltyPercentDiscount;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.PricingService.PricingResult;
import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.pricing.TaxPolicy;

public final class Week6Demo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductFactory productFactory = new ProductFactory();
        Order order = new Order(OrderIds.next());
        DiscountPolicy discountPolicy = new LoyaltyPercentDiscount(5);
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(10);
        PricingService pricingService = new PricingService(discountPolicy, taxPolicy);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        int taxPercent = 10;

        System.out.println("Enter drink order (eg, ESP+SHOT+L or LAT+OAT)");
        System.out.println("Type 'done' when finished");

        while (true) {
            System.out.println("Enter order or type 'done': ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            if (input.isEmpty()) {
                System.out.println("Enter a valid order or type 'done'");
                continue;
            }

            try {
                Product product = productFactory.create(input);
                System.out.println("Enter quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                order.addItem(new LineItem(product, quantity));
                System.out.println("Added: " + product.name() + " x" +quantity);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("");
        System.out.println("ORDER SUMMARY");

        order.items().forEach(li -> {
            PricingService.PricingResult pr = pricingService.price(li.lineTotal());
            String receipt = receiptPrinter.format(li.product().name(), li.quantity(), pr, taxPercent);
            System.out.println(receipt);
            System.out.println("--------------------------------");
        });

        PricingResult pr = pricingService.price(order.subtotal());
        System.out.println("--------------------------------");
        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Discount: -" + pr.discount());
        System.out.println("Tax (" + taxPercent + "%): " + pr.tax());
        System.out.println("Total: " + pr.total());
        System.out.println("--------------------------------");
        System.out.println("");

        System.out.println("Select payment method: [1] Card, [2] Cash, [3] Wallet");
        System.out.println("Enter choice (1,2 or 3): ");
        String choice = scanner.nextLine().trim();

        PaymentStrategy paymentStrategy = switch (choice) {
            case "1"-> {
                System.out.println("Enter card number: ");
                yield new CardPayment(scanner.nextLine().trim());
            }
            case "2" -> 
                new CashPayment();
            case "3" -> {
                System.out.println("Enter wallet ID: ");
                yield new WalletPayment(scanner.nextLine().trim());
            }
            default -> {
                System.out.println("Inalid choice. Defaulting to Cash Payment");
                yield new CashPayment();
            }
        };

        System.out.println("");
        System.out.println("Processing payment....");
        paymentStrategy.pay(order);
        System.out.println("Payment processed");
        scanner.close();
    }
}
