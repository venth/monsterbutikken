package no.borber.monsterbutikken.eventstore;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.UntypedEventsourcedProcessor;

import java.util.ArrayList;
import java.util.List;

public class EventStore extends UntypedEventsourcedProcessor {


    private List<Evt> events = new ArrayList<Evt>();
    private List<ActorRef> subscribers;

    public static Props mkProps() {
        return Props.create(EventStore.class);
    }

    @Override
    public void onReceiveReplay(Object msg) {
        if (msg instanceof Evt) {
            publish((Evt) msg);
        }
    }

    public void onReceiveCommand(Object msg) {
        if (msg instanceof Cmd) {
            Evt evt = new Evt("Mottok en command");

            persist(evt, new Procedure<Evt>() {
                public void apply(Evt evt) throws Exception {
                    publish(evt);
                }
            });
        } else if (msg instanceof Subscription){
            subscribers.add(sender());
        }
    }

    private void publish(Evt event) {
        for (ActorRef subscriber : subscribers)
            subscriber.tell(event, self());
    }
}
