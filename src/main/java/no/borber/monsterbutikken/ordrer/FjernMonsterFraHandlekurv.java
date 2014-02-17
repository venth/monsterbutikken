package no.borber.monsterbutikken.ordrer;

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
