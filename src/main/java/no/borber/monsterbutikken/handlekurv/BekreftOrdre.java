package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Cmd;

public class BekreftOrdre extends Cmd {
    private String kundenavn;

    public BekreftOrdre(String kundenavn) {
        this.kundenavn = kundenavn;
    }

    public String getKundenavn() {
        return kundenavn;
    }
}
