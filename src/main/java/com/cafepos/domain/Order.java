package com.cafepos.domain;

import java.util.List;
import java.util.ArrayList;
import com.cafepos.common.Money;
import com.cafepos.payment.PaymentStrategy;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();

    public Order(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }

    public List<LineItem> items() {
        return List.copyOf(items);
    }

    // 3) Hook notifications into existing behaviors
    public void addItem(LineItem lineItem) {
        if (lineItem == null)
            throw new IllegalArgumentException("Line item required");
        if (lineItem.quantity() <= 0)
            throw new IllegalArgumentException("Quantity must be > 0");
        items.add(lineItem);
        notifyObservers("itemAdded");
    }

    public void removeLastItem() { 
        if (!items.isEmpty()) items.remove(items.size()-1); 
        notifyObservers("itemRemoved");}

    public Money subtotal() {
        return items.stream()
                .map(LineItem::lineTotal)
                .reduce(Money.zero(), Money::add);
    }

    public Money taxAtPercent(int percent) {
        if (percent < 0)
            throw new IllegalArgumentException("Percent must be >= 0");
        Money subtotal = subtotal();
        double taxRate = percent / 100.0;
        return Money.of(subtotal.toDouble() * taxRate);
    }

    public Money totalWithTax(int percent) {
        return subtotal().add(taxAtPercent(percent));
    }

    public void pay(PaymentStrategy strategy) {
        if (strategy == null)
            throw new IllegalArgumentException("strategy required");
        strategy.pay(this);
        notifyObservers("paid");
    }

    // 1) Maintain subscriptions
    private final List<OrderObserver> observers = new ArrayList<>();

    public void register(OrderObserver o) {
        observers.add(o);
    };

    public void unregister(OrderObserver o) {
        observers.remove(o);
    }

    void notifyObservers(String eventType) {
        for (OrderObserver observer : observers) {
            observer.updated(this, eventType);
        }
    }

    public void markReady() {
        notifyObservers("ready");

    }
}
