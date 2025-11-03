package com.cafepos.demo;

import com.cafepos.domain.*;
import com.cafepos.payment.*;

import java.util.Scanner;

import com.cafepos.command.*;

public final class Week8Demo_Commands {
    public static void main(String[] args) {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(3);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a command (add/pay/undo/show/exit) ");

        boolean ordering = true;

        while (ordering) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "add" -> {
                    System.out.println("Enter order (eg, ESP+OAT, CAP+L): ");
                    String drinkOrder = scanner.nextLine().trim();
                    System.out.println("Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine().trim());
                    Command addCommand = new AddItemCommand(service, drinkOrder, quantity);
                    remote.setSlot(0, addCommand);
                    remote.press(0);
                }

                case "pay" -> {
                    System.out.println("Enter pay method (card/cash/wallet)");
                    String payMethod = scanner.nextLine().trim().toLowerCase();
                    PaymentStrategy strategy;

                    switch (payMethod) {
                        case "card" -> {
                            System.out.println("Enter card number: ");
                            String card = scanner.nextLine().trim();
                            strategy = new CardPayment(card);
                        }
                        case "wallet" -> {
                            System.out.println("Enter wallet ID: ");
                            String wallet = scanner.nextLine().trim();
                            strategy = new WalletPayment(wallet);
                        }
                        default -> strategy = new CashPayment();
                    }
                    Command payCommand = new PayOrderCommand(service, strategy, 10);
                    remote.setSlot(1, payCommand);
                    remote.press(1);
                }

                case "undo" -> remote.undo();

                case "show" -> {
                    System.out.println("Current Order: ");
                    for (LineItem item : order.items()) {
                        System.out.println(item.product().name() + " x" + item.quantity());
                    }
                    System.out.println("Total: " + order.totalWithTax(10) + " EUR");
                }

                case "exit" -> {
                    System.out.println("Exiting...");
                    ordering = false;
                }

                default -> System.out.println("Invalid command. (add/pay/undo/show/exit)");
            }
        }
    }
}