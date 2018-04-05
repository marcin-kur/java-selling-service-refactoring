package uj.jwzp.w2.e3;

public class Order {
    private final Item item;
    private final int quantity;
    private final Customer customer;

    public Order(Item item, int quantity, Customer customer) {
        this.item = item;
        this.quantity = quantity;
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Customer getCustomer() {
        return customer;
    }
}
