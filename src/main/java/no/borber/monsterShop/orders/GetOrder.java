package no.borber.monsterShop.orders;

import no.borber.monsterShop.eventstore.Query;

public class GetOrder extends Query{
    private String customerName;
    private String orderId;

    public GetOrder(String customerName, String orderId) {
        this.customerName = customerName;
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderId() {
        return orderId;
    }
}
