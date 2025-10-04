package com.cafepos.common;

import org.junit.jupiter.api.Test;

import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderObserver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class OrderObserverTest {
    @Test
    void observers_notifiedOnItemAdd() {
        Product product = new SimpleProduct("P1", "Coffee", Money.of(10.00));
        Order order = new Order(42);

        List<String> events = new ArrayList<>();
        order.register((o, e) -> events.add(e));
        order.addItem(new LineItem(product, 1));

        assertTrue(events.contains("itemAdded"), "Observer should be notified of itemAdded");
    }

    @Test
    void multipleObservers_recieveReadyEvent() {
        Order order = new Order(42);

        List<String> events1 = new ArrayList<>();
        List<String> events2 = new ArrayList<>();
        order.register((o, e) -> events1.add(e));
        order.register((o, e) -> events2.add(e));
        order.markReady();

        assertTrue(events1.contains("ready"), "First observer should be notified of ready");
        assertTrue(events2.contains("ready"), "Second observer should be notified of ready");
    }

    @Test
    void unregisteredObserver_notNotified() {
        Order order = new Order(42);

        List<String> events = new ArrayList<>();
        OrderObserver observer = (o, e) -> events.add(e);
        order.register(observer);
        order.unregister(observer);
        order.markReady();

        assertTrue(events.isEmpty(), "Unregistered observer should not receive events");
    }
}