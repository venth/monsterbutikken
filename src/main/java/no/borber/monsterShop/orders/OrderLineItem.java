package no.borber.monsterShop.orders;

import java.io.Serializable;

public class OrderLineItem implements Serializable{
    private final String monsterType;
    private final int number;
    private final double price;

    public OrderLineItem(String monsterType, int number, double price) {
        this.monsterType = monsterType;
        this.number = number;
        this.price = price;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }
}
