package com.cafepos.demo;

import java.util.Scanner;

import com.cafepos.common.Product;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.factory.ProductFactory;

public final class Week5Demo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductFactory factory = new ProductFactory();
        Order order = new Order(OrderIds.next());

        System.out.println("Enter drink orders (e.g., ESP+SHOT+OAT or LAT+OAT+L).");
        System.out.println("Type 'done' when finished.");
        System.out.println("");

        while (true) {
            System.out.print("Enter Order: (or 'done'): ");
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("done")) {
                break;
            }
            if (line.isEmpty()) {
                System.out.println("Please enter a valid order or 'done' to finish.");
                continue;
            }

            try {
                Product p = factory.create(line);

                System.out.println("Enter quantity: ");
                int qty = Integer.parseInt(scanner.nextLine().trim());
                if (qty <= 0) {
                    System.out.println("Quantity must be greater than zero.");
                    continue;
                }
                order.addItem(new LineItem(p, qty));
                System.out.println("Added: " + p.name() + " x" + qty);
                System.out.println("");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("");
        System.out.println("ORDER SUMMARY");
        System.out.println("Order #" + order.id());
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name() + " x" + li.quantity() + " = " + li.lineTotal());
        }

        System.out.print("--------------------------------\n");
        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " + order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
        System.out.println("");

        scanner.close();
    }
}
