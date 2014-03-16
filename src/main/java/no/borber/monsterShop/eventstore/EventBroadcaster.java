package no.borber.monsterShop.eventstore;

import akka.actor.Props;
import akka.actor.UntypedActor;
import no.borber.monsterShop.init.Subscription;

import java.util.List;

public class EventBroadcaster extends UntypedActor {

    private List<Subscription> projections;

    public EventBroadcaster(List<Subscription> projections) {
        this.projections = projections;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        for (Subscription subscription : projections) {
             if (subscription.getAggregateTypes().contains(((Event) o).getAggregateType()))
                 subscription.getProjection().tell(o, self());
        }
    }

    public static Props mkProps(List<Subscription> subscriptions) {
        return Props.create(EventBroadcaster.class, subscriptions);
    }
}
