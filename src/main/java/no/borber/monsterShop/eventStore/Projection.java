package no.borber.monsterShop.eventStore;

public abstract class Projection {

    protected final EventStore eventStore;

    public Projection(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public abstract void handleEvent(Event event);

}
