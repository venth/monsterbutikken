package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Query;

public class GetBasket extends Query{
    private String aggregateId;

    public GetBasket(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}
