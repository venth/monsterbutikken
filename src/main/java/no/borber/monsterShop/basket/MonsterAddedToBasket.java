package no.borber.monsterShop.basket;

public class MonsterAddedToBasket extends MonsterShopEvent {
    private String monsterType;
    private double price;

    public MonsterAddedToBasket(String kundenavn, String monsterType, double price) {
        super(kundenavn);
        this.monsterType = monsterType;
        this.price = price;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String getLogMessage() {
        return "Monster added to the baseket of " + getCustomerName();
    }
}
