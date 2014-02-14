package no.borber.monsterbutikken.handlekurv;

public class MonsterFjernetFraHandlekurv extends HandlekurvEvt {
    private String monsterNavn;

    public MonsterFjernetFraHandlekurv(String bruker, String monsterNavn) {
        super(bruker);
        this.monsterNavn = monsterNavn;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }

    @Override
    public String getLogMessage() {
        return "Monster fjernet til i handlekurven til " + getBruker();
    }

}
