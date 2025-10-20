package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.common.Product;

public class OrderManagerGod {
    public static int TAX_PERCENT = 10;     //Global/Static State
    public static String LAST_DISCOUNT_CODE = null;     //Global/Static State

    public static String process(String recipe, int qty, String paymentType, String discountCode,   //God Class & Long method. Handles product creation, pricing, payment, tax calculation, discounts, printing all in one - Bad practice
            boolean printReceipt) {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.decorator.Priced p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }
        if (qty <= 0)   //Primitive Obsession
            qty = 1;
        Money subtotal = unitPrice.multiply(qty);
        Money discount = Money.zero();
        if (discountCode != null) {     //Primitive Obsession
            if (discountCode.equalsIgnoreCase("LOYAL5")) {
                discount = Money.of(subtotal.asBigDecimal()
                        .multiply(java.math.BigDecimal.valueOf(5))
                        .divide(java.math.BigDecimal.valueOf(100)));
            } else if (discountCode.equalsIgnoreCase("COUPON1")) {
                discount = Money.of(1.00);
            } else if (discountCode.equalsIgnoreCase("NONE")) {
                discount = Money.zero();
            } else {
                discount = Money.zero();
            }
            LAST_DISCOUNT_CODE = discountCode;
        }
        Money discounted = Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal())); //Duplicated Logic
        if (discounted.asBigDecimal().signum() < 0)
            discounted = Money.zero();
        var tax = Money.of(discounted.asBigDecimal()    //Shotgun Surgery Risk
                .multiply(java.math.BigDecimal.valueOf(TAX_PERCENT))
                .divide(java.math.BigDecimal.valueOf(100)));
        var total = discounted.add(tax);
        if (paymentType != null) {   //Primitive Obsession
            if (paymentType.equalsIgnoreCase("CASH")) { //Duplicated logic
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }
        StringBuilder receipt = new StringBuilder();    //Long Method
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);
        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}