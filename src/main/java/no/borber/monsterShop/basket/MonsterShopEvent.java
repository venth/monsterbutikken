package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Evt;

public abstract class MonsterShopEvent extends Evt {
    private String customerName;

    public MonsterShopEvent(String customerName) {
        this.customerName = customerName;
    }

    public final String getCustomerName() {
        return customerName;
    }
}
