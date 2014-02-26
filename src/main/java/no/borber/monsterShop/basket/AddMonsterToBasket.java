package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Cmd;

public class AddMonsterToBasket extends Cmd {

    private String customerName;
    private String monsterType;
    private double price;

    public AddMonsterToBasket(String customerName, String monsterType, double price) {
        this.customerName = customerName;
        this.monsterType = monsterType;
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public double getPrice() {
        return price;
    }
}
