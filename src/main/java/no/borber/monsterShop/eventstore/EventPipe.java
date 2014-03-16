package no.borber.monsterShop.eventstore;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.persistence.Persistent;
import akka.persistence.UntypedView;

public class EventPipe extends UntypedView {
    private String processorId;
    private ActorRef eventBroadcaster;

    public EventPipe(String processorId, ActorRef eventBroadcaster) {
        this.processorId = processorId;
        this.eventBroadcaster = eventBroadcaster;
    }

    @Override
    public String processorId() {
        return processorId;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (((Persistent)o).payload() instanceof Event)
            eventBroadcaster.tell(((Persistent) o).payload(), self());
    }

    public static Props mkProps(String processorId) {
        return Props.create(EventPipe.class, processorId);
    }

}
