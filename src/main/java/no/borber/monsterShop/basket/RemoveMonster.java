package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventStore.Command;

public class RemoveMonster extends Command {
    private String monstertype;

    public RemoveMonster(String monstertype) {
        this.monstertype = monstertype;
    }

    public String getMonstertype() {
        return monstertype;
    }
}
