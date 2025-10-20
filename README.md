Removed God Class & Long methods by extracting DiscountPolicy, TaxPolicy, PricingService, ReceiptPrint, CheckoutService.
Replaced Primitive Obesessions such as 'discountCode' strings with typed policy classes
Removed Global/Static State LAST_DISCOUNT_CODE, TAX_PERCENT by passing dependencies in constructor
Removed Duplicated Logic
It follows SOLID principles:
S: Each class has one responsibility now
O: Easily add new discounts etc. without changing old code
L: Classes such as DiscountPolicy and TaxPolicy can be used unterchangeably
I: Extracted Interfaces such as DiscountPolicy and TaxPolicy
D: CheckoutService depends on absractions
Create new class that implements DiscountPolicy to create new discount type
