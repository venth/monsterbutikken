package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Evt;

public abstract class HandlekurvEvt extends Evt {
    private String bruker;

    public HandlekurvEvt(String bruker) {
        this.bruker = bruker;
    }

    public final String getBruker() {
        return bruker;
    }
}
