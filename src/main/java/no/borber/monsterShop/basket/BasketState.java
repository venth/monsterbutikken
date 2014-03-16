package no.borber.monsterShop.basket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketState {
    private String basketId;
    private Map<String, BasketLineItem> basketLineItems = new HashMap<>();


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
}
