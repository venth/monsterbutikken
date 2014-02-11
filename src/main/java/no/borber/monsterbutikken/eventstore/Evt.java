package no.borber.monsterbutikken.eventstore;

import java.io.Serializable;

public class Evt implements Serializable{
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
