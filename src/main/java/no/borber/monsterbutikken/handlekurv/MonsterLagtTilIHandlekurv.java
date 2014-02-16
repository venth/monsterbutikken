package no.borber.monsterbutikken.handlekurv;

public class MonsterLagtTilIHandlekurv extends HandlekurvEvt {
    private String monsterNavn;
    private double pris;

    public MonsterLagtTilIHandlekurv(String bruker, String monsterNavn, double pris) {
        super(bruker);
        this.monsterNavn = monsterNavn;
        this.pris = pris;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }

    public double getPris() {
        return pris;
    }

    @Override
    public String getLogMessage() {
        return "Monster lagt til i handlekurven til " + getBruker();
    }
}
