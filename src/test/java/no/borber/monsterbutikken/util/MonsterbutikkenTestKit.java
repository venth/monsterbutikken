package no.borber.monsterbutikken.util;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.typesafe.config.ConfigFactory;


public class MonsterbutikkenTestKit extends JavaTestKit {

    protected static final ActorSystem _system = ActorSystem.create("monsterbutikken", ConfigFactory.parseResources("classpath:/application.conf"));

    public MonsterbutikkenTestKit() {
        super(_system);
    }

}
