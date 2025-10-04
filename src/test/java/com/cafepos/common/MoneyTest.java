package com.cafepos.common;

import org.junit.jupiter.api.Test;

import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.payment.PaymentStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTest {
    @Test
    void testAdd() {
        Money m1 = Money.of(2.00);
        Money m2 = Money.of(3.00);
        Money result = m1.add(m2);
        assertEquals(Money.of(5.00), result);
    }

    @Test
    void testMultiply() {
        Money m = Money.of(2.50);
        Money result = m.multiply(4);
        assertEquals(Money.of(10.00), result);
    }

    @Test
    void testNoNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(-1.00));
    }

    @Test
    void testNoNegativeMultiply() {
        Money m = Money.of(1.00);
        assertThrows(IllegalArgumentException.class, () -> m.multiply(-2));
    }

    @Test
    void payment_strategy_called() {
        Product p = new SimpleProduct("A", "A", Money.of(5));
        Order order = new Order(42);
        order.addItem(new LineItem(p, 1));
        final boolean[] called = { false };
        PaymentStrategy fake = o -> called[0] = true;
        order.pay(fake);
        assertTrue(called[0], "Payment strategy should be called");
    }

    @Test
    void subtotal_calculatesCorrectly() {
        Product p1 = new SimpleProduct("A", "A", Money.of(5));
        Product p2 = new SimpleProduct("B", "B", Money.of(3));
        Order order = new Order(42);
        order.addItem(new LineItem(p1, 2)); // 10
        order.addItem(new LineItem(p2, 1)); // 3
        assertEquals(Money.of(13), order.subtotal());
    }

    @Test
    void totalWithTax_calculatesCorrectly() {
        Product p1 = new SimpleProduct("A", "A", Money.of(5));
        Product p2 = new SimpleProduct("B", "B", Money.of(3));
        Order order = new Order(42);
        order.addItem(new LineItem(p1, 2)); // 10
        order.addItem(new LineItem(p2, 1)); // 3
        // subtotal = 13
        // tax 10% = 1.3
        // total = 14.3
        assertEquals(Money.of(14.30), order.totalWithTax(10));
    }

    @Test
    void taxAtPercent_calculatesCorrectly() {
        Product p1 = new SimpleProduct("A", "A", Money.of(5));
        Product p2 = new SimpleProduct("B", "B", Money.of(3));
        Order order = new Order(42);
        order.addItem(new LineItem(p1, 2)); // 10
        order.addItem(new LineItem(p2, 1)); // 3
        // subtotal = 13
        // tax 10% = 1.3
        assertEquals(Money.of(1.30), order.taxAtPercent(10));
    }

    @Test
    void addItem_rejectsNull() {
        Order order = new Order(42);
        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }

    @Test
    void addItem_rejectsZeroQuantity() {
        Product p = new SimpleProduct("A", "A", Money.of(5));
        Order order = new Order(42);
        assertThrows(IllegalArgumentException.class, () -> order.addItem(new LineItem(p, 0)));
    }

    @Test
    void pay_rejectsNull() {
        Product p = new SimpleProduct("A", "A", Money.of(5));
        Order order = new Order(42);
        order.addItem(new LineItem(p, 1));
        assertThrows(IllegalArgumentException.class, () -> order.pay(null));
    }

    @Test
    void lineTotal_calculatesCorrectly() {
        Product p = new SimpleProduct("A", "A", Money.of(5));
        LineItem item = new LineItem(p, 3);
        assertEquals(Money.of(15), item.lineTotal());
    }

    
}
