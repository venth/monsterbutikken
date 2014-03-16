package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Command;

public class AddMonsterToBasket extends Command {

    private String monsterType;
    private double price;

    public AddMonsterToBasket(String monsterType, double price) {
        this.monsterType = monsterType;
        this.price = price;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public double getPrice() {
        return price;
    }
}
