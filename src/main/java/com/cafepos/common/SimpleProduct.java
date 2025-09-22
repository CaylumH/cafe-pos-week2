package com.cafepos.common;

public final class SimpleProduct implements Product {
    private final String id;
    private final String name;
    private final Money basePrice;

    public SimpleProduct(String id, String name, Money basePrice) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("id required");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name required");
        if (basePrice == null)
            throw new IllegalArgumentException("basePrice required");
        if (basePrice.compareTo(Money.zero()) < 0)
            throw new IllegalArgumentException("basePrice cannot be negative");
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Money basePrice() {
        return basePrice;
    }
}
