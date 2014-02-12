package no.borber.monsterbutikken.util;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class MonsterbutikkenTestKit extends JavaTestKit {
    private static Config res = ConfigFactory.load().getConfig("monsterbutikken");
    public static ActorSystem _system = ActorSystem.create("monsterbutikken", res);

    public MonsterbutikkenTestKit() {
        super(_system);
    }

}
