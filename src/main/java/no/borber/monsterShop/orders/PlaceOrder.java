package no.borber.monsterShop.orders;

import no.borber.monsterShop.eventstore.Cmd;

public class PlaceOrder extends Cmd {
    private String customerName;

    public PlaceOrder(String kundenavn) {
        this.customerName = kundenavn;
    }

    public String getCustomerName() {
        return customerName;
    }
}
