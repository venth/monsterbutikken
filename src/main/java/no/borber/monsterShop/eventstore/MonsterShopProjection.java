package no.borber.monsterShop.eventstore;

import akka.actor.UntypedActor;
import akka.japi.Procedure;
import akka.persistence.Persistent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MonsterShopProjection extends UntypedActor {
    public static final Logger log = LoggerFactory.getLogger(MonsterShopProjection.class);

    @Override
    final public void onReceive(Object msg) throws Exception {
        if (msg instanceof Event){
            log.info("received event " + msg.getClass());
            Procedure<Event> eventHandler = getEventHandler(msg.getClass());

            if (eventHandler != null)
                eventHandler.apply((Event) msg);
        } else if (msg instanceof Query) {
            getQueryHandler(msg.getClass()).apply((Query) msg);
        }
    }

    protected abstract Procedure<Event> getEventHandler(Class evtClass);

    protected abstract Procedure<Query> getQueryHandler(Class queryClass);

}
