package no.borber.monsterbutikken.handlekurv;

import java.util.List;

public class BekreftetOrdre {
    private List<Ordrelinje> ordrelinjer;

    public BekreftetOrdre(List<Ordrelinje> ordrelinjer) {
        this.ordrelinjer = ordrelinjer;
    }

    public List<Ordrelinje> getOrdrelinjer() {
        return ordrelinjer;
    }
}
