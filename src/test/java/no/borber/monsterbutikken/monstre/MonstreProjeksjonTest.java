package no.borber.monsterbutikken.monstre;

import akka.testkit.TestActorRef;
import no.borber.monsterbutikken.eventstore.Evt;
import no.borber.monsterbutikken.eventstore.Subscription;
import no.borber.monsterbutikken.util.MonsterbutikkenTestKit;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class MonstreProjeksjonTest extends MonsterbutikkenTestKit{

/*    @Test
    public void testName() throws Exception {
        TestActorRef<MonsterProjeksjon> es = TestActorRef.create(_system, MonsterProjeksjon.mkProps(getTestActor()), UUID.randomUUID().toString());
        expectMsgClass(Subscription.class);

        es.tell(new Evt("hupp"), super.getTestActor());
        es.tell("getEvts", super.getTestActor());
        expectMsgClass(List.class);

    }*/
}
