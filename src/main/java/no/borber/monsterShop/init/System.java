package no.borber.monsterShop.init;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import no.borber.monsterShop.basket.BasketProjection;
import no.borber.monsterShop.eventstore.AggregateType;
import no.borber.monsterShop.eventstore.EventBroadcaster;
import no.borber.monsterShop.eventstore.Subscription;
import no.borber.monsterShop.orders.OrdersProjection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class System {

    private final ActorRef orderProjection;
    private final ActorRef eventBroadcaster;
    private ActorRef basketProjection;
    private final ActorSystem actorSystem;

    public System() {
        actorSystem = ActorSystem.create();

        basketProjection = actorSystem.actorOf(BasketProjection.mkProps());
        orderProjection = actorSystem.actorOf(OrdersProjection.mkProps());

        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(basketProjection, AggregateType.BASKET));
        subscriptions.add(new Subscription(orderProjection, AggregateType.ORDER));
        eventBroadcaster = actorSystem.actorOf(EventBroadcaster.mkProps(subscriptions));
    }

    @Bean()
    public ActorSystem getActorSystem() {
        return actorSystem;
    }

    @Bean(name="basketProjection")
    public ActorRef getBasketProjection() {
        return basketProjection;
    }

    @Bean(name="orderProjection")
    public ActorRef getOrderProjection() {
        return orderProjection;
    }

    @Bean(name="eventBroadcaster")
    public ActorRef getEventBroadcaster() {
        return eventBroadcaster;
    }
}
