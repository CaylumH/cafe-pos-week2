package com.cafepos.domain.Observer;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderObserver;

public final class CustomerNotifier implements OrderObserver { 
@Override 
public void updated(Order order, String eventType) { 
// TODO: print "[Customer] Dear customer, your Order #<id> has been updated: <event>." 
} 
} 