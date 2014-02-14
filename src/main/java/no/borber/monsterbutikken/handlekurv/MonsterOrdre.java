package no.borber.monsterbutikken.handlekurv;

public class MonsterOrdre {
    private String monsterNavn;
    private int antall;

    public MonsterOrdre(String monsterNavn) {
        this.monsterNavn = monsterNavn;
    }

    public void addMonster() {
        this.antall++;
    }

    public String getMonsterNavn() {
        return monsterNavn;
    }

    public int getAntall() {
        return antall;
    }

    public void fjernMonster() {
        antall--;
    }
}
