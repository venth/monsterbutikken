package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Query;

public class GetBasket extends Query{
    private String customerName;

    public GetBasket(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
}
