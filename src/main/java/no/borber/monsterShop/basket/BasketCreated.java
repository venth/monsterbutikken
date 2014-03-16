package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.AggregateType;
import no.borber.monsterShop.eventstore.Event;
import scala.collection.immutable.Seq;

public class BasketCreated extends Event{
    private String customerName;

    public BasketCreated(String basketId, String customerName) {
        super(basketId);
        this.customerName = customerName;
    }

    @Override
    public String getLogMessage() {
        return null;
    }

    @Override
    public AggregateType getAggregateType() {
        return AggregateType.BASKET;
    }

    public String getCustomerName() {
        return customerName;
    }
}
