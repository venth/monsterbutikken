package no.borber.monsterShop.init;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import no.borber.monsterShop.basket.BasketHandler;
import no.borber.monsterShop.basket.BasketProjection;
import no.borber.monsterShop.eventstore.EventStore;
import no.borber.monsterShop.orders.OrderHandler;
import no.borber.monsterShop.orders.OrdersProjection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class System {

    private final ActorRef orderProjection;
    private final ActorRef eventStore;
    private final ActorRef ordersHandler;
    private final ActorRef basketHandler;
    private ActorRef basketProjection;

    public System() {
        ActorSystem actorSystem = ActorSystem.create();
        eventStore = actorSystem.actorOf(EventStore.mkProps(), "MonsterShopAggregate");

        basketProjection = actorSystem.actorOf(BasketProjection.mkProps(eventStore.path().toStringWithoutAddress()));
        orderProjection = actorSystem.actorOf(OrdersProjection.mkProps(eventStore.path().toStringWithoutAddress()));

        ordersHandler = actorSystem.actorOf(OrderHandler.mkProps(eventStore, basketProjection));
        basketHandler = actorSystem.actorOf(BasketHandler.mkProps(eventStore));
    }

    @Bean(name="basketProjection")
    public ActorRef getBasketProjection() {
        return basketProjection;
    }

    @Bean(name="orderProjection")
    public ActorRef getOrderProjection() {
        return orderProjection;
    }

    @Bean(name="basketHandler")
    public ActorRef getBasketHandler() {
        return basketHandler;
    }

    @Bean(name="orderHandler")
    public ActorRef getOrdersHandler() {
        return ordersHandler;
    }
}
