package no.borber.monsterbutikken.eventstore;

public class Evt {
    private String value;

    public Evt(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
