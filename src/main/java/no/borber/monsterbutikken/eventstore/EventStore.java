package no.borber.monsterbutikken.eventstore;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.Channel;
import akka.persistence.Deliver;
import akka.persistence.Persistent;
import akka.persistence.UntypedEventsourcedProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EventStore extends UntypedEventsourcedProcessor {

    public static final Logger log = LoggerFactory.getLogger(EventStore.class);
    private final ActorRef channel;
      private List<ActorRef> subscribers = new ArrayList<>();

    public EventStore(List<ActorRef> subscribers) {
        this.channel = getContext().actorOf(Channel.props(), "channel");
        this.subscribers = subscribers;
    }

    public static Props mkProps(List<ActorRef> subscribers) {
        log.debug("created props");
        return Props.create(EventStore.class, subscribers);
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
       }
    }

    private void publish(Evt event) {
        for (ActorRef subscriber : subscribers){
            channel.tell(Deliver.create(Persistent.create(event, getCurrentPersistentMessage()), subscriber.path()), getSelf());
        }
    }
}
