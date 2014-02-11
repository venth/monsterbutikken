package no.borber.monsterbutikken.eventstore;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.UntypedEventsourcedProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EventStore extends UntypedEventsourcedProcessor {

    public static final Logger log = LoggerFactory.getLogger(EventStore.class);


    private List<ActorRef> subscribers = new ArrayList<ActorRef>();

    public static Props mkProps() {
        log.debug("created props");
        return Props.create(EventStore.class);
    }

    @Override
    public void onReceiveRecover(Object msg) {
        log.info("recovering");
        if (msg instanceof Evt) {
            publish((Evt) msg);
        }
    }

    @Override
    public void onReceiveCommand(Object msg) {
        if (msg instanceof Cmd) {
            Evt evt = new Evt(((Cmd) msg).getValue());

            persist(evt, new Procedure<Evt>() {
                public void apply(Evt evt) throws Exception {
                    publish(evt);
                }
            });
        } else if (msg instanceof Subscription){
            log.info("New subscription received");
            subscribers.add(sender());

        }
    }

    private void publish(Evt event) {
        for (ActorRef subscriber : subscribers)
            subscriber.tell(event, self());
    }
}
