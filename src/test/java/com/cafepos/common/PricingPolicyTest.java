package com.cafepos.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cafepos.pricing.LoyaltyPercentDiscount;
import com.cafepos.pricing.NoDiscount;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.ReceiptPrinter;
import com.cafepos.pricing.PricingService.PricingResult;
import com.cafepos.pricing.DiscountPolicy;
import com.cafepos.pricing.FixedCouponDiscount;
import com.cafepos.pricing.FixedRateTaxPolicy;
import com.cafepos.pricing.TaxPolicy;

public class PricingPolicyTest {
    @Test
    void loyalty_discount_of_5_percent() {
        DiscountPolicy d = new LoyaltyPercentDiscount(5);
        assertEquals(Money.of(0.5), d.discountOf(Money.of(10.0)));
    }

    @Test
    void fixed_coupon_discount_of_5_euro() {
        DiscountPolicy d = new FixedCouponDiscount(Money.of(5.00));
        assertEquals(Money.of(4.00), d.discountOf(Money.of(4.00)));
    }

    @Test
    void fixed_rate_tax_of_10_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(10);
        assertEquals(Money.of(1.00), t.taxOn(Money.of(10.00)));
    }

    @Test
    void no_discount_returns_zero() {
        DiscountPolicy d = new NoDiscount();
        assertEquals(Money.zero(), d.discountOf(Money.of(10.00)));
    }

    @Test
    void receipt_formatting() {
        PricingService pricing = new PricingService(new LoyaltyPercentDiscount(10), new FixedRateTaxPolicy(10));
        ReceiptPrinter printer = new ReceiptPrinter();

        PricingResult pr = pricing.price(Money.of(5.00));
        String result = printer.format("Latte + Oat Milk", 2, pr, 10);

        assertTrue(result.contains("Latte + Oat Milk"));
        assertTrue(result.contains("Subtotal"));
        assertTrue(result.contains("Tax"));
        assertTrue(result.contains("Total"));
    }

    @Test
    void pricing_pipeline() {
        PricingService pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        PricingResult pr = pricing.price(Money.of(10.00));
        assertEquals(Money.of(0.50), pr.discount());
        assertEquals(Money.of(0.95), pr.tax());
        assertEquals(Money.of(10.45), pr.total());
    }
}
