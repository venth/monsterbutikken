package no.borber.monsterShop.basket;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class BasketRepository {

    @Autowired
    ActorSystem system;

    @Resource(name = "eventBroadcaster")
    ActorRef eventBroadcaster;

    public ActorRef createBasket(String basketId) {
        return system.actorOf(Basket.mkProps(eventBroadcaster), basketId);
    }

    public ActorSelection getById(String basketId) {
        return system.actorSelection("/user/" + basketId);
    }


}
