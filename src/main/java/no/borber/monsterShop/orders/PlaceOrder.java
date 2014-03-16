package no.borber.monsterShop.orders;

import no.borber.monsterShop.eventstore.Command;

public class PlaceOrder extends Command {
    private String customerName;

    public PlaceOrder(String kundenavn) {
        this.customerName = kundenavn;
    }

    public String getCustomerName() {
        return customerName;
    }
}
