package com.cafepos.decorator;

import com.cafepos.common.Money;
import com.cafepos.common.Product;

public final class OatMilk extends ProductDecorator implements Priced {
    private static final Money SURCHARGE = Money.of(0.50);

    public OatMilk(Product base) {
        super(base);
    }

    @Override
    public String name() {
        return base.name() + " + Oat Milk";
    }

    public Money price() {
        Money basePrice = (base instanceof Priced) ? ((Priced) base).price() : base.basePrice();
        return basePrice.add(SURCHARGE);
    }
}