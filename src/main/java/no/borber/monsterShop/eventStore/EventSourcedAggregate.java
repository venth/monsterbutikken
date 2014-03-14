package no.borber.monsterShop.eventStore;

public abstract class EventSourcedAggregate {
    protected EventStore eventStore;

    public EventSourcedAggregate(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public abstract void handleCommand(Command command);

    public abstract void handleEvent(Event event);
}
