package no.borber.monsterbutikken.autentisering;

public class Bruker {
    private String brukernavn;

    public Bruker(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getBrukernavn() {
        return brukernavn;
    }
}
