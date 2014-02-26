package no.borber.monsterShop;

import no.borber.monsterShop.eventstore.Query;

public class GetOrders extends Query{
    private String customerName;

    public GetOrders(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
}
