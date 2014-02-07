package no.borber.monsterbutikken.eventstore;

public class Cmd {
    private String value;

    public Cmd(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
