package no.borber.monsterbutikken.handlekurv;

public class OrdreBekreftet extends HandlekurvEvt {
    public OrdreBekreftet(String bruker) {
        super(bruker);
    }

    @Override
    public String getLogMessage() {
        return "ordre bekreftet";
    }
}
