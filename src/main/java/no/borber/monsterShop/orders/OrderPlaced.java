package no.borber.monsterShop.orders;

import no.borber.monsterShop.basket.MonsterShopEvent;

import java.util.List;

public class OrderPlaced extends MonsterShopEvent {
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
}
