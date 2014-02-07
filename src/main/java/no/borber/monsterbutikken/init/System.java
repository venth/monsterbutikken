package no.borber.monsterbutikken.init;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import no.borber.monsterbutikken.monstre.MonsterProjeksjon;
import no.borber.monsterbutikken.eventstore.EventStore;

public class System {

    private ActorSystem system;
    private ActorRef eventstore;
    private ActorRef monsterProjeksjon;

    public System() {
        createActorSystem();
        createEventStore();
        createProjections();
    }

    private void createEventStore() {
        eventstore = system.actorOf(EventStore.mkProps());
    }

    private void createProjections() {
        monsterProjeksjon = system.actorOf(MonsterProjeksjon.mkProps());
    }

    private void createActorSystem() {
        system = ActorSystem.create();
    }
}
