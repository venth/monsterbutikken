package no.borber.monsterbutikken.handlekurv;

import no.borber.monsterbutikken.es.Cmd;

public class FjernMonsterFraHandlekurv extends Cmd {
    private final String kundenavn;
    private final String monsternavn;

    public FjernMonsterFraHandlekurv(String kundenavn, String monsternavn) {
        this.kundenavn = kundenavn;
        this.monsternavn = monsternavn;
    }

    public String getMonsternavn() {
        return monsternavn;
    }

    public String getKundenavn() {
        return kundenavn;
    }
}
