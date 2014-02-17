package no.borber.monsterbutikken.init;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import no.borber.monsterbutikken.handlekurv.Ordrer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class System {

    private ActorRef ordrer;

    public System() {
        ActorSystem actorSystem = ActorSystem.create();
        ordrer = actorSystem.actorOf(Ordrer.mkProps());
    }

    @Bean(name="ordrer")
    public ActorRef getOrdrer() {
        return ordrer;
    }
}
