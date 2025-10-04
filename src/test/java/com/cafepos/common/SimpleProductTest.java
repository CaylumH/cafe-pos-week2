package com.cafepos.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cafepos.domain.LineItem;

public class SimpleProductTest {
    @Test
    void constructor_setsFieldsCorrectly() {
        String id = "P1";
        String name = "Coffee";
        Money price = Money.of(10.00);
        SimpleProduct product = new SimpleProduct(id, name, price);
        assertEquals("P1", product.id());
        assertEquals("Coffee", product.name());
        assertEquals(Money.of(10.00), product.basePrice());
    }

    @Test
    void constructor_rejectsNullId() {
        Money price = Money.of(10.00);
        try {
            new SimpleProduct(null, "Coffee", price);
        } catch (IllegalArgumentException e) {
            assertEquals("id required", e.getMessage());
        }
    }

    @Test
    void constructor_rejectsNullName() {
        Money price = Money.of(10.00);
        try {
            new SimpleProduct("P1", null, price);
        } catch (IllegalArgumentException e) {
            assertEquals("name required", e.getMessage());
        }
    }

    @Test
    void constructor_rejectsNullPrice() {
        try {
            new SimpleProduct("P1", "Coffee", null);
        } catch (IllegalArgumentException e) {
            assertEquals("basePrice required", e.getMessage());
        }
    }

    @Test
    void constructor_rejectsEmptyId() {
        Money price = Money.of(10.00);
        try {
            new SimpleProduct("", "Coffee", price);
        } catch (IllegalArgumentException e) {
            assertEquals("id required", e.getMessage());
        }
    }

    @Test
    void constructor_rejectsEmptyName() {
        Money price = Money.of(10.00);
        try {
            new SimpleProduct("P1", "", price);
        } catch (IllegalArgumentException e) {
            assertEquals("name required", e.getMessage());
        }
    }

    @Test
    void constructor_rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class, 
            () -> new SimpleProduct("P1", "Coffee", Money.of(-0.01)));
    }

    @Test
    void product_implementsProductInterface() {
        Money price = Money.of(10.00);
        Product product = new SimpleProduct("P1", "Coffee", price);
        assertTrue(product instanceof Product);
        assertEquals("P1", product.id());
        assertEquals("Coffee", product.name());
        assertEquals(Money.of(10.00), product.basePrice());
    }

    @Test
    void constructor_rejectsNegativeQuantity() {
        Product product = new SimpleProduct("P1", "Coffee", Money.of(10.00));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, -1));
    }
}
