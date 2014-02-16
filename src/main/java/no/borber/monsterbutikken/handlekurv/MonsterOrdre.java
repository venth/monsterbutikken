package no.borber.monsterbutikken.handlekurv;

public class MonsterOrdre {
    private String monsternavn;
    private double pris;
    private int antall;

    public MonsterOrdre(String monsternavn, double pris) {
        this.monsternavn = monsternavn;
        this.pris = pris;
    }

    public void addMonster() {
        this.antall++;
    }

    public String getMonsternavn() {
        return monsternavn;
    }

    public int getAntall() {
        return antall;
    }

    public void fjernMonster() {
        antall--;
    }

    public double getPris() {
        return pris;
    }
}
