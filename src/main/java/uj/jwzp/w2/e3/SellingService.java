package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {

    private final PersistenceLayer persistenceLayer;
    private final DiscountsConfiguration discountsConfiguration;
    private final CustomerMoneyService moneyService;

    public SellingService(PersistenceLayer persistenceLayer, DiscountsConfiguration discountsConfiguration) {
        this.persistenceLayer = persistenceLayer;
        this.discountsConfiguration = discountsConfiguration;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
    }

    public boolean sell(Item item, int quantity, Customer customer) {
        Order order = new Order(item, quantity, customer, discountsConfiguration);
        BigDecimal price = order.calculatePrice();
        boolean sold = moneyService.pay(customer, price);
        if (sold) {
            return persistenceLayer.saveTransaction(customer, item, quantity);
        } else {
            return sold;
        }
    }
}
