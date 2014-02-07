package no.borber.monsterbutikken.monstre;

import akka.actor.Props;

public class MonsterProjeksjon {
    public static Props mkProps() {
        return Props.create(MonsterProjeksjon.class);
    }
}
