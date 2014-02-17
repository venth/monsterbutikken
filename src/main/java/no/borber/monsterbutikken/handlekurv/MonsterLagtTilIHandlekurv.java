package no.borber.monsterbutikken.handlekurv;

public class MonsterLagtTilIHandlekurv extends HandlekurvEvt {
    private String monsternavn;
    private double pris;

    public MonsterLagtTilIHandlekurv(String kundenavn, String monsternavn, double pris) {
        super(kundenavn);
        this.monsternavn = monsternavn;
        this.pris = pris;
    }

    public String getMonsternavn() {
        return monsternavn;
    }

    public double getPris() {
        return pris;
    }

    @Override
    public String getLogMessage() {
        return "Monster lagt til i handlekurven til " + getKundenavn();
    }
}
