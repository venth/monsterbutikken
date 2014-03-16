package no.borber.monsterShop.eventstore;

import java.io.Serializable;

public abstract class Event implements Serializable{

    private String aggregateId;

    public Event(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    abstract public String getLogMessage();

    abstract public AggregateType getAggregateType();

    public String getAggregateId() {
        return aggregateId;
    }
}
