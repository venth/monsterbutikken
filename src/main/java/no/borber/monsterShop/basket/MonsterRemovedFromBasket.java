package no.borber.monsterShop.basket;

public class MonsterRemovedFromBasket extends MonsterShopEvent {
    private String monsterType;

    public MonsterRemovedFromBasket(String customerName, String monsterType) {
        super(customerName);
        this.monsterType = monsterType;
    }

    public String getMonsterType() {
        return monsterType;
    }

    @Override
    public String getLogMessage() {
        return "Monster removed from the baseket of " + getCustomerName();
    }

}
