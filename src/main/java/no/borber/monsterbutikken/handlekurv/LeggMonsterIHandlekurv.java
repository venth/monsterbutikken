package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Cmd;

public class LeggMonsterIHandlekurv extends Cmd {

    private String bruker;
    private String monsterNavn;
    private double pris;

    public LeggMonsterIHandlekurv(String bruker, String monsterNavn, double pris) {
        this.bruker = bruker;
        this.monsterNavn = monsterNavn;
        this.pris = pris;
    }

    public String getBruker() {
        return bruker;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }

    public double getPris() {
        return pris;
    }
}
