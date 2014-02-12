package no.borber.monsterbutikken.eventstore;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.Kill;
import akka.actor.Props;
import akka.persistence.ConfirmablePersistentImpl;
import akka.testkit.TestActorRef;
import no.borber.monsterbutikken.monstre.MonsterProjeksjon;
import no.borber.monsterbutikken.util.MonsterbutikkenTestKit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static akka.pattern.Patterns.ask;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class EventStoreTest extends MonsterbutikkenTestKit{

    @Test
    public void test_at_eventstore_publiserer_hendelser() throws Exception {
        ArrayList<ActorRef> subscribers = new ArrayList<>();
        subscribers.add(getTestActor());
        ActorRef es = _system.actorOf(EventStore.mkProps(subscribers), UUID.randomUUID().toString());
        ActorPath esPath = es.path();

        es.tell(new Cmd("Swordfish"), super.getTestActor());
        expectMsgClass(ConfirmablePersistentImpl.class);
    }

    @Test
    public void test_at_eventstore_publiserer_hendelser_til_projeksjoner() throws Exception {
        ActorRef projection = _system.actorOf(MonsterProjeksjon.mkProps(), UUID.randomUUID().toString());
        ArrayList<ActorRef> subscribers = new ArrayList<>();
        subscribers.add(projection);

        ActorRef es = _system.actorOf(EventStore.mkProps(subscribers), UUID.randomUUID().toString());

        es.tell(new Cmd("Swordfish"), super.getTestActor());
        Thread.sleep(3000);
        List<Evt> events = (List<Evt>) Await.result(ask(projection, "getEvents", 3000), Duration.create("3 seconds"));
        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        assertEquals("Swordfish", events.get(events.size()-1).getValue());
    }

    @Test
    public void test_at_eventstore_republiserer_hendelser_ved_oppstart() throws Exception {
        ArrayList<ActorRef> subscribers = new ArrayList<>();
        subscribers.add(getTestActor());

        String storeId = UUID.randomUUID().toString();
        ActorRef es = _system.actorOf(EventStore.mkProps(subscribers), storeId);

        es.tell(new Cmd("Swordfish"), super.getTestActor());
        expectMsgClass(ConfirmablePersistentImpl.class);

        es.tell(Kill.getInstance(), getTestActor());
        Thread.sleep(250); //wait to ensure es is dead

        _system.actorOf(EventStore.mkProps(subscribers), storeId);
        expectMsgClass(ConfirmablePersistentImpl.class);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("/tmp/journal"));
    }
}
