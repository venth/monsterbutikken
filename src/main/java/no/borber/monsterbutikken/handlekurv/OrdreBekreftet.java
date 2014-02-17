package no.borber.monsterbutikken.handlekurv;

public class OrdreBekreftet extends HandlekurvEvt {
    public OrdreBekreftet(String kundenavn) {
        super(kundenavn);
    }

    @Override
    public String getLogMessage() {
        return "ordre bekreftet";
    }
}
