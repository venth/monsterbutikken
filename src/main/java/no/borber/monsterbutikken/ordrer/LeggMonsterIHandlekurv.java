package no.borber.monsterbutikken.ordrer;

public class LeggMonsterIHandlekurv extends Cmd {

    private String kundenavn;
    private String monsternavn;
    private double pris;

    public LeggMonsterIHandlekurv(String kundenavn, String monsternavn, double pris) {
        this.kundenavn = kundenavn;
        this.monsternavn = monsternavn;
        this.pris = pris;
    }

    public String getKundenavn() {
        return kundenavn;
    }

    public String getMonsternavn() {
        return monsternavn;
    }

    public double getPris() {
        return pris;
    }
}
