package no.borber.monsterShop.eventStore.monsterStore;

import no.borber.monsterShop.basket.Basket;
import no.borber.monsterShop.basket.BasketProjection;
import no.borber.monsterShop.eventStore.Event;
import no.borber.monsterShop.eventStore.EventStore;
import no.borber.monsterShop.orders.OrderAggregate;

public class Repo {

    private static final EventStore monsterEventStore;

    static {
        monsterEventStore = new EventStore();
        basketProjection = new BasketProjection(monsterEventStore);
    }

    private static BasketProjection basketProjection;

    public static Basket getBasket(String aggregateId){
        Basket basketAggregate = new Basket(monsterEventStore);

        for (Event event : monsterEventStore.getAggregateEvents(aggregateId))
            basketAggregate.handleEvent(event);

        return basketAggregate;
    }

    public static Basket createBasket(String aggregateId) {
        return new Basket(monsterEventStore, aggregateId);
    }

    public static BasketProjection getBasketProjection() {
        return basketProjection;
    }

    public static OrderAggregate createOrder(String aggregateId) {
        return new OrderAggregate(monsterEventStore, aggregateId);
    }
}
