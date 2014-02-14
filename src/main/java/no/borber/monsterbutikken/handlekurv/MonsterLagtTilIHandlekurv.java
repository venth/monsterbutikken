package no.borber.monsterbutikken.handlekurv;

public class MonsterLagtTilIHandlekurv extends HandlekurvEvt {
    private String monsterNavn;

    public MonsterLagtTilIHandlekurv(String bruker, String monsterNavn) {
        super(bruker);
        this.monsterNavn = monsterNavn;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }

    @Override
    public String getLogMessage() {
        return "Monster lagt til i handlekurven til " + getBruker();
    }
}
