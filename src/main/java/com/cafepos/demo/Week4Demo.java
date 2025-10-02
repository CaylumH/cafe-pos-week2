package com.cafepos.demo;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.common.Money;
import com.cafepos.common.SimpleProduct;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.domain.Observer.CustomerNotifier;
import com.cafepos.domain.Observer.DeliveryDesk;
import com.cafepos.domain.Observer.KitchenDisplay;
import com.cafepos.payment.CashPayment;

public final class Week4Demo { 
public static void main(String[] args) { 
Catalog catalog = new InMemoryCatalog(); 
catalog.add(new SimpleProduct("P-ESP", "Espresso", 
Money.of(2.50))); 
Order order = new Order(OrderIds.next()); 
order.register(new KitchenDisplay()); 
order.register(new DeliveryDesk()); 
order.register(new CustomerNotifier()); 
order.addItem(new LineItem(catalog.findById("PESP").orElseThrow(), 1)); 
order.pay(new CashPayment()); 
order.markReady(); 
} 
} 