package no.borber.monsterShop.orders;

import no.borber.monsterShop.eventstore.AggregateType;
import no.borber.monsterShop.eventstore.Event;

import java.util.List;

public class OrderPlaced extends Event {
    private final List<OrderLineItem> orderLineItems;

    public OrderPlaced(String customerName, List<OrderLineItem> orderLineItems) {
        super(customerName);
        this.orderLineItems = orderLineItems;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    @Override
    public String getLogMessage() {
        return "Order placed";
    }

    @Override
    public AggregateType getAggregateType() {
        return AggregateType.ORDER;
    }
}
