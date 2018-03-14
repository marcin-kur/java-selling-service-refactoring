package uj.jwzp.w2.e3;

import java.math.BigDecimal;

public class Order {
    private final Item item;
    private final int quantity;
    private final Customer customer;
    private final DiscountsConfiguration discountsConfiguration;

    public Order(Item item, int quantity, Customer customer, DiscountsConfiguration discountsConfiguration) {
        this.item = item;
        this.quantity = quantity;
        this.customer = customer;
        this.discountsConfiguration = discountsConfiguration;
    }

    public BigDecimal calculatePrice() {
        BigDecimal price = getInitPrice();
        return price.subtract(discountDuringWeekendForExpensiveProducts(price));
    }

    private BigDecimal getInitPrice() {
        return item.getPrice().subtract(discountsConfiguration.getDiscountForItem(item, customer)).multiply(BigDecimal.valueOf(quantity));
    }

    private BigDecimal discountDuringWeekendForExpensiveProducts(BigDecimal price) {
        return (discountsConfiguration.isWeekendPromotion() && price.compareTo(BigDecimal.valueOf(5)) > 0)
                ? BigDecimal.valueOf(3)
                : BigDecimal.ZERO;
    }
}
