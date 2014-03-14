package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Event;

public class MonsterRemoved extends Event{
    private String monsterType;

    public MonsterRemoved(String aggregateId, String monsterType) {
        super(aggregateId);
        this.monsterType = monsterType;
    }

    @Override
    public String getAggregateType() {
        return "BASKET";
    }

    public String getMonsterType() {
        return monsterType;
    }

}
