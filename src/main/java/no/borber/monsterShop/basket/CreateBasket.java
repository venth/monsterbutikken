package no.borber.monsterShop.basket;

import no.borber.monsterShop.eventstore.AggregateType;
import no.borber.monsterShop.eventstore.Command;

public class CreateBasket extends Command {
    private final String customerId;

    public CreateBasket(String aggregateId, String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

}
