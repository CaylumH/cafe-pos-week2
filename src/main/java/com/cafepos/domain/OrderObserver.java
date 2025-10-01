package com.cafepos.domain;

public interface OrderObserver {
    void update(Order order, String eventType);
}
