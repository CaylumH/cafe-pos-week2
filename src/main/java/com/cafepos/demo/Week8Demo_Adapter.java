package com.cafepos.demo;

import java.util.Scanner;

import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.printing.*;
import vendor.legacy.LegacyThermalPrinter;

public final class Week8Demo_Adapter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        Printer printer = new LegacyPrinterAdapter(new LegacyThermalPrinter());

        boolean ordering = true;
        while (ordering) {
            System.out.println("Enter command (print/exit): ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "print" -> {
                    System.out.println("Enter order (eg, ESP+OAT, CAP+L): ");
                    String drinkOrder = scanner.nextLine().trim().toLowerCase();
                    System.out.println("Enter quantity: ");
                    int quantity;
                    try {
                        quantity = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity ");
                        continue;
                    }

                    System.out.println("Enter subtotal (eg, 8.40): ");
                    String subtotal = scanner.nextLine().trim();

                    System.out.println("Enter tax percentage (eg, 10): ");
                    String taxPercent = scanner.nextLine().trim();

                    String receiptText = String.format(
                        "Order (%s) x%d\nSubtotal: %s\nTax %s%%: %.2f\nTotal: %.2f",
                        drinkOrder,
                        quantity,
                        subtotal,
                        taxPercent,
                        Double.parseDouble(subtotal) * Double.parseDouble(taxPercent) / 100,
                        Double.parseDouble(subtotal) * (1 + Double.parseDouble(taxPercent) / 100)
                    );

                    printer.print(receiptText);
                    System.out.println("[Demo] Sent receipt via adapter.");
                }

                case "exit" -> {
                    ordering = false;
                    System.out.println("Exiting...");
                }

                default -> System.out.println("Invalid command (print/exit)");
            }
        }
        scanner.close();
    }
}