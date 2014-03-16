package no.borber.monsterShop.basket;

import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.UntypedEventsourcedProcessor;
import no.borber.monsterShop.eventstore.Command;
import no.borber.monsterShop.eventstore.Event;

import java.util.HashMap;
import java.util.Map;

public class Basket extends UntypedEventsourcedProcessor {

    private Map<Class<? extends Command>, Procedure<Command>> commandHandlers = new HashMap<>();
    private Map<Class<? extends Event>, Procedure<Event>> eventHandlers = new HashMap<>();
    private BasketState basketState;

    public static Props mkProps() {
        return Props.create(Basket.class);
    }

    public Basket() {
        initCommandHandlers();
        initEventHandlers();
    }

    @Override
    public void onReceiveRecover(Object o) {
        if (o instanceof Event)
            handleEvent((Event) o);

    }

    @Override
    public void onReceiveCommand(Object msg) {
        if (msg instanceof Command)
            handleCommand((Command) msg);
    }

    private void initCommandHandlers() {
        commandHandlers.put(CreateBasket.class, new Procedure<Command>() {
            public void apply(Command command) throws Exception {
               CreateBasket cmd = (CreateBasket) command;
                persist(new BasketCreated(basketState.getBasketId(), cmd.getCustomerId()), getEventHandler(BasketCreated.class));
            }
        });

        commandHandlers.put(AddMonsterToBasket.class, new Procedure<Command>() {
            public void apply(Command command) throws Exception {
                AddMonsterToBasket cmd = (AddMonsterToBasket) command;
                persist(new MonsterAddedToBasket(basketState.getBasketId(), cmd.getMonsterType(), cmd.getPrice()), getEventHandler(MonsterAddedToBasket.class));
            }
        });
        commandHandlers.put(RemoveMonsterFromBasket.class, new Procedure<Command>() {
            public void apply(Command command) throws Exception {
                RemoveMonsterFromBasket cmd = (RemoveMonsterFromBasket) command;
                persist(new MonsterRemovedFromBasket(basketState.getBasketId(), cmd.getMonsterType()), getEventHandler(MonsterRemovedFromBasket.class));
            }
        });
    }

    private void initEventHandlers() {
        eventHandlers.put(MonsterAddedToBasket.class, new Procedure<Event>() {
            public void apply(Event event) throws Exception {
                MonsterAddedToBasket evt = (MonsterAddedToBasket) event;
                basketState.addMonster(evt.getMonsterType(), evt.getPrice());
            }
        });
        eventHandlers.put(MonsterRemovedFromBasket.class, new Procedure<Event>() {
            public void apply(Event event) throws Exception {
                MonsterRemovedFromBasket evt = (MonsterRemovedFromBasket) event;
                basketState.removeMonster(evt.getMonsterType());
            }
        });
    }

    private Procedure<Event> getEventHandler(Class<? extends Event> eventClass) {
        return eventHandlers.get(eventClass);
    }

    private Procedure<Command> getCommandHandler(Class<? extends Command> eventClass) {
        return commandHandlers.get(eventClass);
    }

    private void handleEvent(Event event) {
        try {
            getEventHandler(event.getClass()).apply(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCommand(Command command){
        try {
            getCommandHandler(command.getClass()).apply(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
