package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Cmd;

public class BekreftOrdre extends Cmd {
    private String bruker;

    public BekreftOrdre(String bruker) {
        this.bruker = bruker;
    }

    public String getBruker() {
        return bruker;
    }
}
