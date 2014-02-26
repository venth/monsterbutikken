package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Cmd;

public class RemoveMonsterFromBasket extends Cmd {
    private final String customerName;
    private final String monsterType;

    public RemoveMonsterFromBasket(String customerName, String monsterType) {
        this.customerName = customerName;
        this.monsterType = monsterType;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getCustomerName() {
        return customerName;
    }
}
