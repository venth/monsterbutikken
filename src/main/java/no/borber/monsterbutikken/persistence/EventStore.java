package no.borber.monsterbutikken.persistence;

import akka.actor.Props;
import akka.persistence.UntypedEventsourcedProcessor;

public class EventStore extends UntypedEventsourcedProcessor {

    public static Props mkProps() {
        return Props.create(EventStore.class);
    }

    @Override
    public void onReceiveReplay(Object msg) {
        if (msg instanceof Evt) {
            publish((Evt) msg);
    }

    @Override
    public void onReceiveCommand(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

    private void publish(Evt msg) {
        //To change body of created methods use File | Settings | File Templates.
    }
