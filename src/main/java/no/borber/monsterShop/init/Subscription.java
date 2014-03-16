package no.borber.monsterShop.init;

import akka.actor.ActorRef;
import no.borber.monsterShop.eventstore.AggregateType;

import java.util.Arrays;
import java.util.List;

public class Subscription {
    private final ActorRef basketProjection;
    private final List<AggregateType> aggregateTypes;

    public Subscription(ActorRef basketProjection, AggregateType... aggregateTypes) {
        this.basketProjection = basketProjection;
        this.aggregateTypes = Arrays.asList(aggregateTypes);
    }

    public List<AggregateType> getAggregateTypes() {
        return aggregateTypes;
    }

    public ActorRef getProjection() {
        return basketProjection;
    }
}
