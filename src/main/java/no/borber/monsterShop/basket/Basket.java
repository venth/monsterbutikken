package no.borber.monsterShop.basket;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.UntypedEventsourcedProcessor;
import no.borber.monsterShop.eventstore.Command;
import no.borber.monsterShop.eventstore.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Basket extends UntypedEventsourcedProcessor {

    private final ActorRef eventBroadcaster;
    private Map<Class<? extends Command>, Procedure<Command>> commandHandlers = new HashMap<>();
    private Map<Class<? extends Event>, Procedure<Event>> eventHandlers = new HashMap<>();
    private BasketState basketState = new BasketState();
    public static final Logger log = LoggerFactory.getLogger(Basket.class);

    public static Props mkProps(ActorRef eventBroadcaster) {
        return Props.create(Basket.class, eventBroadcaster);
    }

    public Basket(ActorRef eventBroadcaster) {
        this.eventBroadcaster = eventBroadcaster;
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
        log.info("received command " + msg);
        if (msg instanceof Command)
            handleCommand((Command) msg);
    }

    private void initCommandHandlers() {
        commandHandlers.put(CreateBasket.class, new Procedure<Command>() {
            public void apply(Command command) throws Exception {
               CreateBasket cmd = (CreateBasket) command;
               persist(new BasketCreated(cmd.getBasketId(), cmd.getCustomerId()), getEventHandler(BasketCreated.class));
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
        eventHandlers.put(BasketCreated.class, new Procedure<Event>() {
            public void apply(Event event) throws Exception {
                BasketCreated evt = (BasketCreated) event;
                basketState.setBasketId(evt.getAggregateId());
                basketState.setCustomerName(evt.getCustomerName());
                publish(event);
            }
        });
        eventHandlers.put(MonsterAddedToBasket.class, new Procedure<Event>() {
            public void apply(Event event) throws Exception {
                MonsterAddedToBasket evt = (MonsterAddedToBasket) event;
                basketState.addMonster(evt.getMonsterType(), evt.getPrice());
                publish(event);
            }
        });
        eventHandlers.put(MonsterRemovedFromBasket.class, new Procedure<Event>() {
            public void apply(Event event) throws Exception {
                MonsterRemovedFromBasket evt = (MonsterRemovedFromBasket) event;
                basketState.removeMonster(evt.getMonsterType());
                publish(event);
            }
        });
    }

    private void publish(Event event) {
        eventBroadcaster.tell(event, self());
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
