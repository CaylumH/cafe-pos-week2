package com.cafepos.domain.Observer;

public final class DeliveryDesk implements OrderObserver { 
@Override 
public void updated(Order order, String eventType) { 
// TODO: on "ready" -> print "[Delivery] Order #<id> is ready for delivery" 
} 
} 