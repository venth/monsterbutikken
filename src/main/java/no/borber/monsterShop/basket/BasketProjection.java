package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Event;
import no.borber.monsterShop.eventStore.EventStore;
import no.borber.monsterShop.eventStore.Projection;

import java.util.HashMap;
import java.util.Map;

public class BasketProjection extends Projection {
    private Map<String, Map<String, BasketItem>> baskets = new HashMap<>();

    public BasketProjection(EventStore monsterEventStore) {
        super(monsterEventStore);
        eventStore.subscribe(this, "BASKET");
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof BasketCreated){
            baskets.put(event.getAggregateId(), new HashMap<String, BasketItem>());
        } else if (event instanceof MonsterAdded) {
            if (baskets.get(event.getAggregateId()).get(((MonsterAdded) event).getMonsterType()) == null){
                BasketItem item = new BasketItem(((MonsterAdded) event).getMonsterType(), ((MonsterAdded) event).getPrice());
                baskets.get(event.getAggregateId()).put(((MonsterAdded) event).getMonsterType(), item);
            } else {
                BasketItem basketItem = baskets.get(event.getAggregateId()).get(((MonsterAdded) event).getMonsterType());
                basketItem.addMonster();
            }

        } else if (event instanceof MonsterRemoved) {
            BasketItem basketItem = baskets.get(event.getAggregateId()).get(((MonsterRemoved) event).getMonsterType());
            basketItem.removeMonster();
            if (basketItem.getNumber() == 0)
                baskets.get(event.getAggregateId()).remove(((MonsterRemoved) event).getMonsterType());
        }
    }

    public Map<String, BasketItem> getBasket(String aggregateId) {
        return baskets.get(aggregateId);
    }

    public double getBasketSum(String aggregateId) {
        double sum = 0;
        for (BasketItem basketItem : baskets.get(aggregateId).values())
            sum = sum + basketItem.getPrice();
        return sum;
    }
}
