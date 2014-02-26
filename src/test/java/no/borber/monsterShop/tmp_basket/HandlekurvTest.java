/*
package no.borber.monsterShop.tmp_basket;

import akka.actor.ActorRef;
import akka.actor.Kill;
import no.borber.monsterShop.basket.AddMonsterToBasket;
import no.borber.monsterShop.basket.Baskets;
import no.borber.monsterShop.basket.RemoveMonsterFromBasket;
import no.borber.monsterShop.util.MonsterbutikkenTestKit;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import static akka.pattern.Patterns.ask;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HandlekurvTest extends MonsterbutikkenTestKit{

    @Test
    public void test_at_handlekurvens_tilstand_endrer_seg_nar_monster_legges_til() throws Exception {
        ActorRef es = _system.actorOf(Baskets.mkProps(), UUID.randomUUID().toString());

        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        Map<String, Ordrelinje> ordrer = (Map<String, Ordrelinje>) Await.result(ask(es, "Dr. Evil", 3000), Duration.create("3 seconds"));
        assertNotNull(ordrer);
        assertFalse(ordrer.isEmpty());
        assertNotNull(ordrer.get("Kraken"));
    }

    @Test
    public void test_at_antall_monstre_av_en_type_stiger_nar_flere_legges_til() throws Exception {
        ActorRef es = _system.actorOf(Baskets.mkProps(), UUID.randomUUID().toString());

        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());

        Map<String, Ordrelinje> ordrer = (Map<String, Ordrelinje>) Await.result(ask(es, "Dr. Evil", 3000), Duration.create("3 seconds"));
        assertNotNull(ordrer);
        assertFalse(ordrer.isEmpty());
        assertEquals(2, ordrer.get("Kraken").getAntall());
    }

    @Test
    public void test_at_antall_monstre_av_en_type_synker_nar_monstre_fjernes() throws Exception {
        ActorRef es = _system.actorOf(Baskets.mkProps(), UUID.randomUUID().toString());

        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        es.tell(new RemoveMonsterFromBasket("Dr. Evil", "Kraken"), super.getTestActor());

        Map<String, Ordrelinje> ordrer = (Map<String, Ordrelinje>) Await.result(ask(es, "Dr. Evil", 3000), Duration.create("3 seconds"));
        assertNotNull(ordrer);
        assertFalse(ordrer.isEmpty());
        assertEquals(1, ordrer.get("Kraken").getAntall());
    }

    @Test
    public void test_at_monster_forsvinner_fra_handlekurv_nar_antall_synker_til_0() throws Exception {
        ActorRef es = _system.actorOf(Baskets.mkProps(), UUID.randomUUID().toString());

        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        es.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        es.tell(new RemoveMonsterFromBasket("Dr. Evil", "Kraken"), super.getTestActor());
        es.tell(new RemoveMonsterFromBasket("Dr. Evil", "Kraken"), super.getTestActor());

        Map<String, Ordrelinje> ordrer = (Map<String, Ordrelinje>) Await.result(ask(es, "Dr. Evil", 3000), Duration.create("3 seconds"));
        assertNotNull(ordrer);
        assertTrue(ordrer.isEmpty());
    }

    @Test
    public void test_at_handlekurven_rebygger_tilstand_ved_restart() throws Exception {
        String storeId = UUID.randomUUID().toString();
        ActorRef handlekurv = _system.actorOf(Baskets.mkProps(), storeId);

        handlekurv.tell(new AddMonsterToBasket("Dr. Evil", "Kraken", 100), super.getTestActor());
        Map<String, Ordrelinje> ordrer = (Map<String, Ordrelinje>) Await.result(ask(handlekurv, "Dr. Evil", 3000), Duration.create("3 seconds"));
        assertNotNull(ordrer);
        assertFalse(ordrer.isEmpty());
        assertEquals("Kraken", ordrer.get("Kraken").getMonsternavn());

        handlekurv.tell(Kill.getInstance(), getTestActor());
        Thread.sleep(250); //wait to ensure handlekurv is dead

        ActorRef gjenskaptHandlekurv = _system.actorOf(Baskets.mkProps(), storeId);
        Map<String, Ordrelinje> gjenskapteOrdrer = (Map<String, Ordrelinje>) Await.result(ask(gjenskaptHandlekurv, "Dr. Evil", 3000), Duration.create("3 seconds"));
        assertNotNull(gjenskapteOrdrer);
        assertFalse(gjenskapteOrdrer.isEmpty());
        assertEquals("Kraken", gjenskapteOrdrer.get("Kraken").getMonsternavn());

    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("/tmp/journal"));
    }


}
*/
