package com.cafepos.domain;

import java.util.concurrent.atomic.AtomicLong;

public final class OrderIds {
    private static final AtomicLong NEXT_ID = new AtomicLong(1000); // starting order ID

    private OrderIds() {} // prevent instantiation

    public static long next() {
        return NEXT_ID.getAndIncrement();
    }
}
