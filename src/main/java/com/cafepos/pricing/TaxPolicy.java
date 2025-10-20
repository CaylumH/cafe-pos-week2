package com.cafepos.pricing;

import com.cafepos.common.Money;

import java.math.BigDecimal;

public interface TaxPolicy {
    Money taxOn(Money amount);
}
