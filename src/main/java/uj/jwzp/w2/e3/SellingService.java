package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {

    private final PersistenceLayer persistenceLayer;
    private final OrderService orderService;
    private final CustomerMoneyService moneyService;

    public SellingService(PersistenceLayer persistenceLayer, DiscountsConfiguration discountsConfiguration) {
        this.persistenceLayer = persistenceLayer;
        this.orderService = new OrderService(discountsConfiguration);
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
    }

    public boolean sell(Item item, int quantity, Customer customer) {
        Order order = new Order(item, quantity, customer);
        BigDecimal price = orderService.calculatePrice(order);
        boolean sold = moneyService.pay(customer, price);
        if (sold) {
            return persistenceLayer.saveTransaction(customer, item, quantity);
        } else {
            return sold;
        }
    }

    public CustomerMoneyService getMoneyService() {
        return moneyService;
    }
}
