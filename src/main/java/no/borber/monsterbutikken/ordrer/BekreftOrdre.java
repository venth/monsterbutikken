package no.borber.monsterbutikken.ordrer;

public class BekreftOrdre extends Cmd {
    private String kundenavn;

    public BekreftOrdre(String kundenavn) {
        this.kundenavn = kundenavn;
    }

    public String getKundenavn() {
        return kundenavn;
    }
}
