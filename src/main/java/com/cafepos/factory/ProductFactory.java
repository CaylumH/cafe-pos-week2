package com.cafepos.factory;

import com.cafepos.common.Money;
import com.cafepos.common.Product;
import com.cafepos.common.SimpleProduct;
import com.cafepos.decorator.*;

public final class ProductFactory {
    public Product create(String recipe) {
        if (recipe == null || recipe.isBlank())
            throw new IllegalArgumentException("recipe required");
        String[] raw = recipe.split("\\+"); // literal '+'
        String[] parts = java.util.Arrays.stream(raw)
                .map(String::trim)
                .map(String::toUpperCase)
                .toArray(String[]::new);

        Product p;
        switch (parts[0]) {
            case "ESP":
                p = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
                break;
            case "LAT":
                p = new SimpleProduct("P-LAT", "Latte", Money.of(3.20));
                break;
            case "CAP":
                p = new SimpleProduct("P-CAP", "Cappuccino", Money.of(3.00));
                break;
            default:
                throw new IllegalArgumentException("Unknown base: " + parts[0]);
        };

        for (int i = 1; i < parts.length; i++) {
            switch (parts[i]) {
                case "SHOT":
                    p = new ExtraShot(p);
                    break;
                case "OAT":
                    p = new OatMilk(p);
                    break;
                case "SYRUP":
                    p = new Syrup(p);
                    break;
                case "L":
                    p = new SizeLarge(p);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown addon: " + parts[i]);
            };
        }
        return p;
    }
}