package no.borber.monsterShop.orders;

import akka.actor.Props;
import akka.japi.Procedure;
import com.google.common.collect.ImmutableMap;
import no.borber.monsterShop.GetOrders;
import no.borber.monsterShop.eventstore.Event;
import no.borber.monsterShop.eventstore.MonsterShopProjection;
import no.borber.monsterShop.eventstore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrdersProjection extends MonsterShopProjection{

    private Map<String, Map<String, Order>> orders = new HashMap<>();
    private Map<Class, Procedure<Event>> eventHandlers = new HashMap<>();
    private Map<Class, Procedure<Query>> queryHandlers = new HashMap<>();

    public static Props mkProps() {
        return Props.create(OrdersProjection.class);
    }

    public OrdersProjection() {
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
        queryHandlers.put(GetOrder.class, new Procedure<Query>() {
            public void apply(Query query) throws Exception {
                GetOrder getOrder = (GetOrder) query;
                Map<String, Order> customerOrders = orders.get(getOrder.getCustomerName());
                if (customerOrders != null)
                    sender().tell(customerOrders.get(getOrder.getOrderId()), self());
                else
                    log.error("no orders found!");
            }
        });
        queryHandlers.put(GetOrders.class, new Procedure<Query>() {
            public void apply(Query query) throws Exception {
                GetOrders getOrders = (GetOrders) query;
                Map<String, Order> customerOrders = orders.get(getOrders.getCustomerName());
                if (customerOrders != null)
                    sender().tell(ImmutableMap.copyOf(customerOrders), self());
                else
                    sender().tell(ImmutableMap.of(), self());
            }
        });
    }

    private void initEventHandlers() {
        eventHandlers.put(OrderPlaced.class, new Procedure<Event>() {
            public void apply(Event evt) throws Exception {
                OrderPlaced orderPlaced = (OrderPlaced) evt;

                if (orders.get(orderPlaced.getAggregateId()) == null){
                    Map<String, Order> customerOrders = new HashMap<>();
                    orders.put(orderPlaced.getAggregateId(), customerOrders);

                }

                orders.get(orderPlaced.getAggregateId()).put(UUID.randomUUID().toString(), new Order(new Date(), 300, orderPlaced.getOrderLineItems()));
            }
        });

    }
}
