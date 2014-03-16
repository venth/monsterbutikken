package no.borber.monsterShop.basket;

import java.util.HashMap;
import java.util.Map;

public class BasketState {
    private String basketId;
    private Map<String, BasketLineItem> basketLineItems = new HashMap<>();
    private String customerName;


    public String getBasketId() {
        return basketId;
    }

    public void addMonster(String monsterType, double price) {
        if (basketLineItems.get(monsterType) == null)
            basketLineItems.put(monsterType, new BasketLineItem(monsterType, price));
        else
            basketLineItems.get(monsterType).addMonster();
    }

    public void removeMonster(String monsterType) {
        basketLineItems.get(monsterType).removeMonster();
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
}
