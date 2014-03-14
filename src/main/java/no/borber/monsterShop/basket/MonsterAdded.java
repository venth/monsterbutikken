package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Event;

public class MonsterAdded extends Event{
    private String monsterType;
    private double price;

    public MonsterAdded(String aggregateId, String monsterType, double price) {
        super(aggregateId);
        this.monsterType = monsterType;
        this.price = price;
    }

    @Override
    public String getAggregateType() {
        return "BASKET";
    }

    public String getMonsterType() {
        return monsterType;
    }

    public double getPrice() {
        return price;
    }
}
