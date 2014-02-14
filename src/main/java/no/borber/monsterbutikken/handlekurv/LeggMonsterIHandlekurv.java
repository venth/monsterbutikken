package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Cmd;

public class LeggMonsterIHandlekurv extends Cmd {

    private String bruker;
    private String monsterNavn;

    public LeggMonsterIHandlekurv(String bruker, String monsterNavn) {
        this.bruker = bruker;
        this.monsterNavn = monsterNavn;
    }

    public String getBruker() {
        return bruker;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }
}
