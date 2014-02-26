package no.borber.monsterShop.eventstore;

import java.io.Serializable;

public abstract class Evt implements Serializable{

    abstract public String getLogMessage();

}
