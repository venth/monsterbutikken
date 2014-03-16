package no.borber.monsterShop.eventstore;

import akka.actor.Props;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventBroadcaster extends UntypedActor {

    private List<Subscription> projections;
    public static final Logger log = LoggerFactory.getLogger(EventBroadcaster.class);

    public EventBroadcaster(List<Subscription> projections) {
        this.projections = projections;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        log.info("event broadcaster recieved " + o);
        for (Subscription subscription : projections) {
             if (subscription.getAggregateTypes().contains(((Event) o).getAggregateType()))
                 subscription.getProjection().tell(o, self());
        }
    }

    public static Props mkProps(List<Subscription> subscriptions) {
        return Props.create(EventBroadcaster.class, subscriptions);
    }
}
