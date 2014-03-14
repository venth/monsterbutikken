package no.borber.monsterShop.eventStore;

public abstract class Event {
    private String aggregateId;

    public Event(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public abstract String getAggregateType();
}
