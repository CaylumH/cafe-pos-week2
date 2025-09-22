package com.cafepos.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;

    public static Money of(double value) {
        BigDecimal bd = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
        if (bd.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Negative amounts are not allowed");
        }
        return new Money(bd);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    private Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        if (a.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Negative amounts are not allowed");
        }
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }

    public Money add(Money other) {
        if (other == null) throw new IllegalArgumentException("other quantity required");
        BigDecimal result = this.amount.add(other.amount).setScale(2, RoundingMode.HALF_UP);
        return new Money(result);
    }

    public Money multiply(int qty) {
        if (qty < 0) throw new IllegalArgumentException("Negative quantity not allowed");
        BigDecimal result = this.amount.multiply(BigDecimal.valueOf(qty)).setScale(2, RoundingMode.HALF_UP);
        return new Money(result);
    }

    @Override
    public int compareTo(Money other) {
        if (other == null) throw new IllegalArgumentException("other quantity required");
        return this.amount.compareTo(other.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money other = (Money) o;
        return this.amount.equals(other.amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return "$" + amount.toString();
    }

    public double toDouble() {
    return amount.doubleValue();
}

}
