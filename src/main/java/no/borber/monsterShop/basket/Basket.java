package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Command;
import no.borber.monsterShop.eventStore.Event;
import no.borber.monsterShop.eventStore.EventSourcedAggregate;
import no.borber.monsterShop.eventStore.EventStore;

public class Basket extends EventSourcedAggregate {
    private String aggregateId;

    public Basket(EventStore monsterEventStore) {
        super(monsterEventStore);
    }

    public Basket(EventStore monsterEventStore, String aggregateId) {
        super(monsterEventStore);
        eventStore.store(new BasketCreated(aggregateId));
        this.aggregateId = aggregateId;
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof AddMonster)
            eventStore.store(new MonsterAdded(aggregateId, ((AddMonster) command).getMonstertype(), ((AddMonster) command).getPrice()));
        else if (command instanceof RemoveMonster)
            eventStore.store(new MonsterRemoved(aggregateId, ((RemoveMonster) command).getMonstertype()));
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof BasketCreated)
            this.aggregateId = event.getAggregateId();
    }
}
