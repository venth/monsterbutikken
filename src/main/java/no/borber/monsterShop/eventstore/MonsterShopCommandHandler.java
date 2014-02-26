package no.borber.monsterShop.eventstore;

import akka.actor.UntypedActor;
import akka.japi.Procedure;

public abstract class MonsterShopCommandHandler extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Cmd)
            getCommandHandler(msg.getClass()).apply((Cmd) msg);

    }

    protected abstract Procedure<Cmd> getCommandHandler(Class cmdClass);
}
