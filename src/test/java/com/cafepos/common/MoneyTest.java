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
}
