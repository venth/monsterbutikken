package no.borber.monsterShop.basket;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import no.borber.monsterShop.basket.Basket;
import no.borber.monsterShop.eventstore.EventPipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class BasketRepository {

    @Autowired
    ActorSystem system;

    public ActorRef createBasket(String basketId) {
        system.actorOf(EventPipe.mkProps(basketId));
        return system.actorOf(Basket.mkProps(), basketId);
    }

    public ActorRef getById(String basketId) {
        return system.actorSelection(basketId).anchor();
    }


}
