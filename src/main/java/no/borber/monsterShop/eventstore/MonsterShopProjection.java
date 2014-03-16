package no.borber.monsterShop.eventstore;

import akka.actor.UntypedActor;
import akka.japi.Procedure;
import akka.persistence.Persistent;
import akka.persistence.UntypedView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MonsterShopProjection extends UntypedActor {
    public static final Logger log = LoggerFactory.getLogger(MonsterShopProjection.class);
    private String eventStorePath;

    @Override
    final public void onReceive(Object msg) throws Exception {
        if (msg instanceof Persistent){
            log.debug("received event " + ((Persistent) msg).payload().getClass());
            Procedure<Event> eventHandler = getEventHandler(((Persistent) msg).payload().getClass());

            if (eventHandler != null)
                eventHandler.apply((Event) ((Persistent) msg).payload());
        } else if (msg instanceof Query) {
            getQueryHandler(msg.getClass()).apply((Query) msg);
        }
    }

    protected abstract Procedure<Event> getEventHandler(Class evtClass);

    protected abstract Procedure<Query> getQueryHandler(Class queryClass);

}
