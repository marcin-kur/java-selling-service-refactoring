package uj.jwzp.w2.e3;

import java.math.BigDecimal;

public class OrderService {
    private final DiscountsConfiguration discountsConfiguration;

    public OrderService(DiscountsConfiguration discountsConfiguration) {
        this.discountsConfiguration = discountsConfiguration;
    }

    public BigDecimal calculatePrice(Order order) {
        BigDecimal price = getInitPrice(order.getItem(), order.getCustomer(), discountsConfiguration, order.getQuantity());
        return price.subtract(discountDuringWeekendForExpensiveProducts(discountsConfiguration, price));
    }

    private BigDecimal getInitPrice(Item item, Customer customer, DiscountsConfiguration discountsConfiguration, int quantity) {
        return item.getPrice()
                .subtract(discountsConfiguration.getDiscountForItem(item, customer))
                .multiply(BigDecimal.valueOf(quantity));
    }

    private BigDecimal discountDuringWeekendForExpensiveProducts(DiscountsConfiguration discountsConfiguration, BigDecimal price) {
        return (discountsConfiguration.isWeekendPromotion() && price.compareTo(BigDecimal.valueOf(5)) > 0)
                ? BigDecimal.valueOf(3)
                : BigDecimal.ZERO;
    }
}