package no.borber.monsterbutikken.monstre;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.persistence.ConfirmablePersistentImpl;
import akka.persistence.UntypedView;
import no.borber.monsterbutikken.eventstore.Evt;
import no.borber.monsterbutikken.eventstore.Subscription;

import java.util.ArrayList;
import java.util.List;

public class MonsterProjeksjon extends UntypedView {

    List<Evt> events = new ArrayList<>();

    public static Props mkProps() {
        return Props.create(MonsterProjeksjon.class);
    }

    /*
    public MonsterProjeksjon(ActorRef eventStore) {
        eventStore.tell(new Subscription(), self());
    }*/


    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof ConfirmablePersistentImpl)
            events.add((Evt) ((ConfirmablePersistentImpl) msg).payload());
        else
            sender().tell(events, self());

    }

    @Override
    public String processorId() {
        return "eventStore";
    }
}
