package com.cafepos.domain.observer;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderObserver;

public final class KitchenDisplay implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
        if (eventType.equals("itemAdded")) {
            System.out.println("[Kitchen] Order #" + order.id() + ": item added");
        } else if (eventType.equals("paid")) {
            System.out.println("[Kitchen] Order #" + order.id() + ": payment received");

        }
    }
}