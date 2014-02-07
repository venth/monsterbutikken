package no.borber.monsterbutikken.monstre;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import no.borber.monsterbutikken.eventstore.Evt;
import no.borber.monsterbutikken.eventstore.Subscription;

import java.util.ArrayList;
import java.util.List;

public class MonsterProjeksjon extends UntypedActor{

    List<Evt> events = new ArrayList<>();

    public static Props mkProps(ActorRef eventStore) {
        return Props.create(MonsterProjeksjon.class, eventStore);
    }

    public MonsterProjeksjon(ActorRef eventStore) {
        eventStore.tell(new Subscription(), self());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Evt)
            events.add((Evt) msg);

    }
}
