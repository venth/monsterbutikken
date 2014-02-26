package no.borber.monsterShop.eventstore;

import akka.japi.Procedure;
import akka.persistence.Persistent;
import akka.persistence.UntypedView;
import no.borber.monsterShop.basket.MonsterShopEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MonsterShopProjection extends UntypedView {
    public static final Logger log = LoggerFactory.getLogger(MonsterShopProjection.class);
    private String eventStorePath;

    public MonsterShopProjection(String eventStorePath) {
        this.eventStorePath = eventStorePath;
    }

    @Override
    final public String processorId() {
        return eventStorePath;
    }

    @Override
    final public void onReceive(Object msg) throws Exception {
        if (msg instanceof Persistent){
            log.debug("received event " + ((Persistent) msg).payload().getClass());
            Procedure<MonsterShopEvent> eventHandler = getEventHandler(((Persistent) msg).payload().getClass());

            if (eventHandler != null)
                eventHandler.apply((MonsterShopEvent) ((Persistent) msg).payload());
        } else if (msg instanceof Query) {
            getQueryHandler(msg.getClass()).apply((Query) msg);
        }
    }

    protected abstract Procedure<MonsterShopEvent> getEventHandler(Class evtClass);

    protected abstract Procedure<Query> getQueryHandler(Class queryClass);

    @Override
    public abstract String viewId();
}
