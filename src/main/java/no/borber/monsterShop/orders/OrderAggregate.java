package no.borber.monsterShop.orders;

import no.borber.monsterShop.eventStore.Command;
import no.borber.monsterShop.eventStore.Event;
import no.borber.monsterShop.eventStore.EventSourcedAggregate;
import no.borber.monsterShop.eventStore.EventStore;

public class OrderAggregate extends EventSourcedAggregate{
    private final String aggregateId;

    public OrderAggregate(EventStore monsterEventStore, String aggregateId) {
        super(monsterEventStore);
        this.aggregateId = aggregateId;
    }

    @Override
    public void handleCommand(Command command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleEvent(Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
