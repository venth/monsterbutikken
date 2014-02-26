package no.borber.monsterShop.orders;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.Persistent;
import no.borber.monsterShop.basket.BasketLineItem;
import no.borber.monsterShop.basket.GetBasket;
import no.borber.monsterShop.eventstore.AcknowledgePrevious;
import no.borber.monsterShop.eventstore.Cmd;
import no.borber.monsterShop.eventstore.MonsterShopCommandHandler;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static akka.pattern.Patterns.ask;

public class OrderHandler extends MonsterShopCommandHandler {

    private final ActorRef eventStore;
    private ActorRef basketProjection;
    private Map<Class, Procedure<Cmd>> commandHandlers = new HashMap<>();

    public static Props mkProps(ActorRef eventStore, ActorRef basketProjection) {
        return Props.create(OrderHandler.class, eventStore, basketProjection);
    }

    public OrderHandler(ActorRef eventStore, ActorRef basketProjection) {
        this.eventStore = eventStore;
        this.basketProjection = basketProjection;
        initCommandHandlers();
    }

    private void initCommandHandlers() {
        commandHandlers.put(PlaceOrder.class, new Procedure<Cmd>() {
            public void apply(Cmd cmd) throws Exception {

                PlaceOrder placeOrder = (PlaceOrder) cmd;
                Map<String, BasketLineItem> customerOrders;
                try {
                     customerOrders = (Map<String, BasketLineItem>) Await.result(ask(basketProjection, new GetBasket(placeOrder.getCustomerName()), 3000), Duration.create("3 seconds"));
                } catch (Exception e) {
                    throw new RuntimeException("feil under henting av handlekurv", e);
                }

                List<OrderLineItem> orderLineItems = new ArrayList<>();
                for (BasketLineItem item : customerOrders.values()){
                    orderLineItems.add(new OrderLineItem(item.getMonsterType(), item.getNumber(), item.getPrice()));
                }

                eventStore.tell(Persistent.create(new OrderPlaced(placeOrder.getCustomerName(), orderLineItems)), self());
                eventStore.tell(new AcknowledgePrevious(), sender());
            }
        });

    }

    @Override
    protected Procedure<Cmd> getCommandHandler(Class cmdClass) {
           return commandHandlers.get(cmdClass);
    }


}
