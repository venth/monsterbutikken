package no.borber.monsterbutikken.eventstore;

import akka.actor.Actor;
import akka.testkit.TestActorRef;
import no.borber.monsterbutikken.util.MonsterbutikkenTestKit;
import org.junit.Test;

import java.util.UUID;

public class EventStoreTest extends MonsterbutikkenTestKit{

    @Test
    public void test_at_es_publiserer_og_endrer_tilstand_nar_event_mottas() throws Exception {
        TestActorRef<Actor> es = TestActorRef.create(_system, EventStore.mkProps(), UUID.randomUUID().toString());

        es.tell(new Cmd("hupp"), super.testActor());
        expectMsgClass(Evt.class);
    }
}
