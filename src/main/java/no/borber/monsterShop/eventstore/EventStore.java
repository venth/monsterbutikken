package no.borber.monsterShop.eventstore;

import akka.actor.Props;
import akka.persistence.PersistenceFailure;
import akka.persistence.Persistent;
import akka.persistence.UntypedProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventStore extends UntypedProcessor{

    public static final Logger log = LoggerFactory.getLogger(EventStore.class);

    public static Props mkProps() {
        return Props.create(EventStore.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Persistent) {
            Persistent persistent = (Persistent)message;
            log.debug("message " + persistent.payload() + " was successfully written to the journal of ");
        } else if (message instanceof PersistenceFailure) {
            PersistenceFailure failure = (PersistenceFailure)message;
            log.error("Journaling of message " + failure.payload() + " failed", failure.cause());
        } else if (message instanceof AcknowledgePrevious) {
            sender().tell(new Success(), self());
        }
    }
}
