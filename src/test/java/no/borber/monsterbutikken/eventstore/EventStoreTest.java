package no.borber.monsterbutikken.eventstore;

import akka.actor.ActorRef;
import akka.actor.Kill;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import no.borber.monsterbutikken.util.MonsterbutikkenTestKit;
import org.junit.Test;

import java.util.UUID;

public class EventStoreTest extends MonsterbutikkenTestKit{

    @Test
    public void test_at_es_publiserer_mottatte_events_mottas() throws Exception {
        ActorRef es = _system.actorOf(Props.create(EventStore.class), "EventStore");

        es.tell(new Subscription(), super.getTestActor());
        es.tell(new Cmd("hupp"), super.getTestActor());
        expectMsgClass(Evt.class);
    }

    @Test
    public void test_at_es_republiserer_mottatte_events_pa_oppstart() throws Exception {
        ActorRef es = _system.actorOf(Props.create(EventStore.class), "EventStore");

        es.tell(new Subscription(), super.getTestActor());
        es.tell(new Cmd("hupp"), super.getTestActor());
        expectMsgClass(Evt.class);
        es.tell(Kill.getInstance(), super.getTestActor());
        Thread.sleep(3000);
        ActorRef revivedEs = _system.actorOf(Props.create(EventStore.class), "EventStore");



        expectMsgClass(Evt.class);
    }
}
