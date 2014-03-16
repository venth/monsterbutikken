package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.Command;

public class RemoveMonsterFromBasket extends Command {
    private final String monsterType;

    public RemoveMonsterFromBasket(String monsterType) {
        this.monsterType = monsterType;
    }

    public String getMonsterType() {
        return monsterType;
    }

}
