package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Command;

public class AddMonster extends Command {
    private String monstertype;
    private double price;

    public AddMonster(String monstertype, double price) {
        this.monstertype = monstertype;
        this.price = price;
    }

    public String getMonstertype() {
        return monstertype;
    }

    public double getPrice() {
        return price;
    }
}
