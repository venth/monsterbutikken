package no.borber.monsterbutikken.init;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import no.borber.monsterbutikken.handlekurv.Handlekurv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class System {

    private ActorRef handlekurv;

    public System() {
        ActorSystem system = ActorSystem.create();
        handlekurv = system.actorOf(Handlekurv.mkProps());
    }

    @Bean(name="handlekurv")
    private ActorRef getHandlekurv() {
        return handlekurv;
    }
}
