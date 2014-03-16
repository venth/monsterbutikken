package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.AggregateType;
import no.borber.monsterShop.eventstore.Command;

public class CreateBasket extends Command {
    private final String customerId;
    private String basketId;

    public CreateBasket(String basketId, String customerId) {
        this.basketId = basketId;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBasketId() {
        return basketId;
    }
}
