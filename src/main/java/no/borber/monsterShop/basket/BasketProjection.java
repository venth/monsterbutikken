package no.borber.monsterShop.basket;

import akka.actor.Props;
import akka.japi.Procedure;
import com.google.common.collect.ImmutableMap;
import no.borber.monsterShop.eventstore.MonsterShopProjection;
import no.borber.monsterShop.eventstore.Query;
import no.borber.monsterShop.orders.OrderPlaced;

import java.util.HashMap;
import java.util.Map;

public class BasketProjection extends MonsterShopProjection{

    private Map<String, Map<String, BasketLineItem>> baskets = new HashMap<>();
    private Map<Class, Procedure<MonsterShopEvent>> eventHandlers = new HashMap<>();
    private Map<Class, Procedure<Query>> queryHandlers = new HashMap<>();
    private String eventStorePath;

    public static Props mkProps(String eventStorePath) {
        return Props.create(BasketProjection.class, eventStorePath);
    }

    public BasketProjection(String eventStorePath) {
        super(eventStorePath);
        initEventHandlers();
        initQueryHandlers();
    }

    @Override
    protected Procedure<MonsterShopEvent> getEventHandler(Class evtClass) {
        return eventHandlers.get(evtClass);
    }

    @Override
    protected Procedure<Query> getQueryHandler(Class queryClass) {
        return queryHandlers.get(queryClass);
    }

    @Override
    public String viewId() {
        return "BasketProjection";
    }

    private void initQueryHandlers() {
        queryHandlers.put(GetBasket.class, new Procedure<Query>() {
            public void apply(Query query) throws Exception {
                GetBasket getBasket = (GetBasket) query;
                if (baskets.get(getBasket.getCustomerName()) != null)
                    sender().tell(ImmutableMap.copyOf(baskets.get(getBasket.getCustomerName())), self());
                else
                    sender().tell(ImmutableMap.of(), self());
            }
        });
    }

    private void initEventHandlers() {
        eventHandlers.put(MonsterAddedToBasket.class, new Procedure<MonsterShopEvent>() {
            public void apply(MonsterShopEvent evt) throws Exception {
                MonsterAddedToBasket monsterAddedToBasket = (MonsterAddedToBasket) evt;
                if (baskets.get(monsterAddedToBasket.getCustomerName()) == null)
                    baskets.put(monsterAddedToBasket.getCustomerName(), new HashMap<String, BasketLineItem>());

                if (baskets.get(monsterAddedToBasket.getCustomerName()).get(monsterAddedToBasket.getMonsterType()) == null)
                    baskets.get(monsterAddedToBasket.getCustomerName()).put(monsterAddedToBasket.getMonsterType(), new BasketLineItem(monsterAddedToBasket.getMonsterType(), monsterAddedToBasket.getPrice()));

                baskets.get(monsterAddedToBasket.getCustomerName()).get(monsterAddedToBasket.getMonsterType()).addMonster();
            }
        });

        eventHandlers.put(MonsterRemovedFromBasket.class, new Procedure<MonsterShopEvent>() {
            public void apply(MonsterShopEvent evt) throws Exception {
                MonsterRemovedFromBasket monsterRemovedFromBasket = (MonsterRemovedFromBasket) evt;
                BasketLineItem basketLineItem = baskets.get(monsterRemovedFromBasket.getCustomerName()).get(monsterRemovedFromBasket.getMonsterType());

                basketLineItem.removeMonster();
                if (basketLineItem.getNumber() == 0)
                    baskets.get(monsterRemovedFromBasket.getCustomerName()).remove(basketLineItem.getMonsterType());
            }
        });

        eventHandlers.put(OrderPlaced.class, new Procedure<MonsterShopEvent>() {
            public void apply(MonsterShopEvent evt) throws Exception {
                baskets.remove(evt.getCustomerName());
            }
        });
    }


}
