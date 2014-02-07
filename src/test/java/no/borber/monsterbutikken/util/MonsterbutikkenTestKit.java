package no.borber.monsterbutikken.util;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import com.typesafe.config.ConfigFactory;

public class MonsterbutikkenTestKit extends TestKit {

    protected static final ActorSystem _system = ActorSystem.create("monsterbutikken", ConfigFactory.parseResources("classpath:/application.conf"));

    public MonsterbutikkenTestKit() {
        super(_system);
    }

}
