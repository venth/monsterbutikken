package no.borber.monsterbutikken.ordrer;

public class MonsterFjernetFraHandlekurv extends HandlekurvEvt {
    private String monsternavn;

    public MonsterFjernetFraHandlekurv(String kundenavn, String monsternavn) {
        super(kundenavn);
        this.monsternavn = monsternavn;
    }

    public String getMonsternavn() {
        return monsternavn;
    }

    @Override
    public String getLogMessage() {
        return "Monster fjernet til i handlekurven til " + getKundenavn();
    }

}
