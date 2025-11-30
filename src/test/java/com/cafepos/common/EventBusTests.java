package com.cafepos.common;

import com.cafepos.app.events.EventBus;
import com.cafepos.app.events.OrderCreated;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventBusTests {
    @Test void handler_receives_event() {
        EventBus bus = new EventBus();
        final int[] count = {0};
        bus.on(OrderCreated.class, e -> count[0]++);
        bus.emit(new OrderCreated(1L));
        assertEquals(1, count[0]);
    }

    @Test void handler_receives_multiple_events() {
        EventBus bus = new EventBus();
        final int[] count = {0};
        bus.on(OrderCreated.class, e -> count[0]++);
        bus.emit(new OrderCreated(2L));
        bus.emit(new OrderCreated(3L));
        assertEquals(2, count[0]);
    }

    @Test void multiple_handlers_receive_event() {
        EventBus bus = new EventBus();
        final int[] counterA = {0};
        final int[] counterB = {0};
        final OrderCreated[] captured = new OrderCreated[1];

        bus.on(OrderCreated.class, e -> counterA[0]++);
        bus.on(OrderCreated.class, e -> { counterB[0]++; captured[0] = e; });

        OrderCreated ev = new OrderCreated(5L);
        bus.emit(ev);

        assertEquals(1, counterA[0]);
        assertEquals(1, counterB[0]);
        assertSame(ev, captured[0]);
    }

    @Test void handler_receives_same_event_instance() {
        EventBus bus = new EventBus();
        final OrderCreated[] received = new OrderCreated[1];
        OrderCreated ev = new OrderCreated(42L);

        bus.on(OrderCreated.class, e -> received[0] = e);
        bus.emit(ev);

        assertSame(ev, received[0]);
    }
}