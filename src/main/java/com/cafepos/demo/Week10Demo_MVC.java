package com.cafepos.demo;

import java.util.Scanner;

import com.cafepos.infra.Wiring;
import com.cafepos.ui.OrderController;
import com.cafepos.ui.ConsoleView;

public final class Week10Demo_MVC {
    public static void main(String[] args) {
        var c = Wiring.createDefault();
        var controller = new OrderController(c.repo(), c.checkout());
        var view = new ConsoleView();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter order ID: ");
        long id = Long.parseLong(scanner.nextLine());
        controller.createOrder(id);

        while (true) {
            System.out.println("Choose an option (add/checkout/exit): ");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "add" -> {
                    System.out.print("Enter recipe (eg, ESP+OAT, CAP+L): ");
                    String recipe = scanner.nextLine().trim();
                    System.out.println("Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    controller.addItem(id, recipe, quantity);
                    System.out.println("[MVC] Added item.");
                }

                case "checkout" -> {
                    System.out.print("Enter tax percantage (%): ");
                    int tax = Integer.parseInt(scanner.nextLine());
                    String receipt = controller.checkout(id, tax);
                    view.print(receipt);
                }

                case "exit" -> {
                    System.out.println("Exiting demo");
                    return;
                }

                default -> System.out.println("Invalid input");
            }
        }
    }
}
