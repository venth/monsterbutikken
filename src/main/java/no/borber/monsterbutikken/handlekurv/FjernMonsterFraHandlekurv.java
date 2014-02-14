package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Cmd;

public class FjernMonsterFraHandlekurv extends Cmd {
    private final String bruker;
    private final String monsterNavn;

    public FjernMonsterFraHandlekurv(String bruker, String monsterNavn) {
        this.bruker = bruker;
        this.monsterNavn = monsterNavn;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }

    public String getBruker() {
        return bruker;
    }
}
