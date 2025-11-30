Caylum Hurley - 22356363
Joe Considine - 22344977

Github: https://github.com/CaylumH/cafe-pos-week2

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


Week 10 Questions:

We used a Layered Monolith instead of splitting the system into independent services. It keeps the development more simple, isolating from I/O and it makes the code easier to evolve and test later on.

The most natural candidates for future partitioning are the Payments, Observers, Receipt Printing, and the Pricing service, since these classes already use interfaces and publish events.

We think it would make sense to bring in REST APIs for synchronous operations such as payment authorisation and also bring in domain events for asynchronous workflows such as order creation.