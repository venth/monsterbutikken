package no.borber.monsterbutikken.ordrer;

public abstract class HandlekurvEvt extends Evt {
    private String kundenavn;

    public HandlekurvEvt(String kundenavn) {
        this.kundenavn = kundenavn;
    }

    public final String getKundenavn() {
        return kundenavn;
    }
}
