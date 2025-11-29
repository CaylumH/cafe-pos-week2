package com.cafepos.ui;

import java.util.Scanner;

import com.cafepos.app.events.*;
import com.cafepos.infra.Wiring;

public final class EventWiringDemo {
    public static void main(String[] args) {
        var bus = new EventBus();
        var comp = Wiring.createDefault();
        var controller = new OrderController(comp.repo(), comp.checkout());
        Scanner scanner = new Scanner(System.in);

        bus.on(OrderCreated.class, e -> System.out.println("[UI] Order created event received: "+ e.orderId()));
        bus.on(OrderPaid.class, e -> System.out.println("[UI] Order paid event received: " + e.orderId()));

        System.out.println("Enter order ID: ");
        long id = Long.parseLong(scanner.nextLine());

        while (true) {
            System.out.println("Choose an event (create/pay/exit): ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "create" -> {
                    controller.createOrder(id);
                    bus.emit(new OrderCreated(id));
                }

                case "pay" -> {
                    bus.emit(new OrderCreated(id));
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
