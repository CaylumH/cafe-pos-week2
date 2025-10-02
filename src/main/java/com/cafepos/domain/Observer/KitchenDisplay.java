package com.cafepos.domain.Observer;

public final class KitchenDisplay implements OrderObserver { 
@Override 
public void updated(Order order, String eventType) { 
// TODO: on "itemAdded" -> print "[Kitchen] Order #<id>: item added" 
//       on "paid"      -> print "[Kitchen] Order #<id>: payment received" 
} 
}