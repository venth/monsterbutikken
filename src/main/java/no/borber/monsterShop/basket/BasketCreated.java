package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Event;

public class BasketCreated extends Event {
    public BasketCreated(String aggregateId) {
        super(aggregateId);
    }

    @Override
    public String getAggregateType() {
        return "BASKET";
    }
}
