package no.borber.monsterShop.basket;

public class BasketLineItem {
    private String monsterType;
    private double price;
    private int number;

    public BasketLineItem(String monsterType, double price) {
        this.monsterType = monsterType;
        this.price = price;
    }

    public void addMonster() {
        this.number++;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public int getNumber() {
        return number;
    }

    public void removeMonster() {
        number--;
    }

    public double getPrice() {
        return price;
    }
}
