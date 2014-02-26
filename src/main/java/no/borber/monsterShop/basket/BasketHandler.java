package no.borber.monsterShop.basket;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.Persistent;
import no.borber.monsterShop.eventstore.AcknowledgePrevious;
import no.borber.monsterShop.eventstore.Cmd;
import no.borber.monsterShop.eventstore.MonsterShopCommandHandler;

import java.util.HashMap;
import java.util.Map;

public class BasketHandler extends MonsterShopCommandHandler {

    private final ActorRef eventStore;
    private Map<Class, Procedure<Cmd>> commandHandlers = new HashMap<>();

    public static Props mkProps(ActorRef eventStore) {
        return Props.create(BasketHandler.class, eventStore);
    }

    public BasketHandler(ActorRef eventStore) {
        this.eventStore = eventStore;
        initCommandHandlers();
    }

    private void initCommandHandlers() {
        commandHandlers.put(AddMonsterToBasket.class, new Procedure<Cmd>() {
            public void apply(Cmd cmd) throws Exception {
                AddMonsterToBasket addMonster = (AddMonsterToBasket) cmd;
                eventStore.tell(Persistent.create(new MonsterAddedToBasket(addMonster.getCustomerName(), addMonster.getMonsterType(), addMonster.getPrice())), self());
                eventStore.tell(new AcknowledgePrevious(), sender());
            }
        });
        commandHandlers.put(RemoveMonsterFromBasket.class, new Procedure<Cmd>() {
            public void apply(Cmd cmd) throws Exception {
                RemoveMonsterFromBasket removeMonster = (RemoveMonsterFromBasket) cmd;
                eventStore.tell(Persistent.create(new MonsterRemovedFromBasket(removeMonster.getCustomerName(), removeMonster.getMonsterType())), self());
                eventStore.tell(new AcknowledgePrevious(), sender());
            }
        });
    }

    @Override
    protected Procedure<Cmd> getCommandHandler(Class cmdClass) {
           return commandHandlers.get(cmdClass);
    }


}
