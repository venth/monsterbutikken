package no.borber.monsterShop.basket;

import akka.actor.Props;
import akka.japi.Procedure;
import com.google.common.collect.ImmutableMap;
import no.borber.monsterShop.eventstore.Event;
import no.borber.monsterShop.eventstore.MonsterShopProjection;
import no.borber.monsterShop.eventstore.Query;
import no.borber.monsterShop.orders.OrderPlaced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BasketProjection extends MonsterShopProjection {

    private Map<String, Map<String, BasketLineItem>> baskets = new HashMap<>();
    private Map<Class, Procedure<Event>> eventHandlers = new HashMap<>();
    private Map<Class, Procedure<Query>> queryHandlers = new HashMap<>();


    public static Props mkProps() {
        return Props.create(BasketProjection.class);
    }

    public BasketProjection() {
        initEventHandlers();
        initQueryHandlers();
    }

    @Override
    protected Procedure<Event> getEventHandler(Class evtClass) {
        return eventHandlers.get(evtClass);
    }

    @Override
    protected Procedure<Query> getQueryHandler(Class queryClass) {
        return queryHandlers.get(queryClass);
    }

    private void initQueryHandlers() {
        queryHandlers.put(GetBasket.class, new Procedure<Query>() {
            public void apply(Query query) throws Exception {
                GetBasket getBasket = (GetBasket) query;
                if (baskets.get(getBasket.getAggregateId()) != null)
                    sender().tell(ImmutableMap.copyOf(baskets.get(getBasket.getAggregateId())), self());
                else
                    sender().tell(ImmutableMap.of(), self());
            }
        });
    }

    private void initEventHandlers() {
        eventHandlers.put(BasketCreated.class, new Procedure<Event>() {
            public void apply(Event evt) throws Exception {
                BasketCreated basketCreated = (BasketCreated) evt;
                if (baskets.get(basketCreated.getAggregateId()) == null)
                    baskets.put(basketCreated.getAggregateId(), new HashMap<String, BasketLineItem>());
            }
        });

        eventHandlers.put(MonsterAddedToBasket.class, new Procedure<Event>() {
            public void apply(Event evt) throws Exception {
                MonsterAddedToBasket monsterAddedToBasket = (MonsterAddedToBasket) evt;
                if (baskets.get(monsterAddedToBasket.getAggregateId()) == null)
                    baskets.put(monsterAddedToBasket.getAggregateId(), new HashMap<String, BasketLineItem>());

                if (baskets.get(monsterAddedToBasket.getAggregateId()).get(monsterAddedToBasket.getMonsterType()) == null)
                    baskets.get(monsterAddedToBasket.getAggregateId()).put(monsterAddedToBasket.getMonsterType(), new BasketLineItem(monsterAddedToBasket.getMonsterType(), monsterAddedToBasket.getPrice()));

                baskets.get(monsterAddedToBasket.getAggregateId()).get(monsterAddedToBasket.getMonsterType()).addMonster();
            }
        });

        eventHandlers.put(MonsterRemovedFromBasket.class, new Procedure<Event>() {
            public void apply(Event evt) throws Exception {
                MonsterRemovedFromBasket monsterRemovedFromBasket = (MonsterRemovedFromBasket) evt;
                BasketLineItem basketLineItem = baskets.get(monsterRemovedFromBasket.getAggregateId()).get(monsterRemovedFromBasket.getMonsterType());

                basketLineItem.removeMonster();
                if (basketLineItem.getNumber() == 0)
                    baskets.get(monsterRemovedFromBasket.getAggregateId()).remove(basketLineItem.getMonsterType());
            }
        });

    }


}
