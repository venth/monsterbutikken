package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Evt;

public abstract class HandlekurvEvt extends Evt {
    private String kundenavn;

    public HandlekurvEvt(String kundenavn) {
        this.kundenavn = kundenavn;
    }

    public final String getKundenavn() {
        return kundenavn;
    }
}
