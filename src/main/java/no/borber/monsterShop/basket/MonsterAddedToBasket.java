package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.AggregateType;
import no.borber.monsterShop.eventstore.Event;

public class MonsterAddedToBasket extends Event {
    private String monsterType;
    private double price;

    public MonsterAddedToBasket(String aggregateId, String monsterType, double price) {
        super(aggregateId);
        this.monsterType = monsterType;
        this.price = price;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String getLogMessage() {
        return "Monster added to the baseket";
    }

    @Override
    public AggregateType getAggregateType() {
        return AggregateType.BASKET;
    }
}
