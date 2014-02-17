package no.borber.monsterbutikken.ordrer;

import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.UntypedEventsourcedProcessor;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ordrer extends UntypedEventsourcedProcessor {

    public static final Logger log = LoggerFactory.getLogger(Ordrer.class);

    private Map<String, Map<String, Ordrelinje>> handlekurver = new HashMap<>();
    private Map<String, List<BekreftetOrdre>> bekreftedeOrdrer = new HashMap<>();
    private Map<Class, Procedure<HandlekurvEvt>> eventHandlers = new HashMap<>();

    public static Props mkProps() {
        return Props.create(Ordrer.class);
    }

    public Ordrer() {
        initEventHandlers();
    }

    @Override
    public void onReceiveRecover(Object msg) {
        if (msg instanceof Evt){
            try {
                getHandler(msg.getClass()).apply((HandlekurvEvt) msg);
            } catch (Exception e) {
                log.error("feil unnder recover", e);
            }
        }
    }

    @Override
    public void onReceiveCommand(Object msg) {
        if (msg instanceof Cmd) {
            handleCommand((Cmd) msg);
        } else if (msg instanceof String){
            if (handlekurver.get(msg) != null)
                sender().tell(ImmutableMap.copyOf(handlekurver.get(msg)), self());
            else
                sender().tell(ImmutableMap.of(), self());
        }
    }

    private void handleCommand(final Cmd command) {
        if (command instanceof LeggMonsterIHandlekurv){
            final String monsterNavn = ((LeggMonsterIHandlekurv) command).getMonsternavn();
            final String kundenavn = ((LeggMonsterIHandlekurv) command).getKundenavn();
            final double pris = ((LeggMonsterIHandlekurv) command).getPris();

            if (monsterNavn != null && kundenavn != null) {
                persist(new MonsterLagtTilIHandlekurv(kundenavn, monsterNavn, pris), getHandler(MonsterLagtTilIHandlekurv.class));
            } else
                sender().tell("Invalid command!", self());
        } else if (command instanceof FjernMonsterFraHandlekurv){
            final String monsterNavn = ((FjernMonsterFraHandlekurv) command).getMonsternavn();
            final String kundenavn = ((FjernMonsterFraHandlekurv) command).getKundenavn();

            if (monsterNavn != null && kundenavn != null) {
                persist(new MonsterFjernetFraHandlekurv(kundenavn, monsterNavn), getHandler(MonsterFjernetFraHandlekurv.class));
            } else
                sender().tell("Invalid command!", self());
        } else if (command instanceof BekreftOrdre){
            final String bruker = ((BekreftOrdre) command).getKundenavn();

            if (bruker != null) {
                persist(new OrdreBekreftet(bruker), getHandler(OrdreBekreftet.class));
            } else
                sender().tell("Invalid command!", self());
        }
    }

    private Procedure<HandlekurvEvt> getHandler(Class evtClass) {
        return eventHandlers.get(evtClass);
    }

    private void initEventHandlers() {
        eventHandlers.put(MonsterLagtTilIHandlekurv.class, new Procedure<HandlekurvEvt>() {
            public void apply(HandlekurvEvt evt) throws Exception {
                MonsterLagtTilIHandlekurv monsterLagtTilIHandlekurv = (MonsterLagtTilIHandlekurv) evt;
                if (handlekurver.get(monsterLagtTilIHandlekurv.getKundenavn()) == null)
                    handlekurver.put(monsterLagtTilIHandlekurv.getKundenavn(), new HashMap<String, Ordrelinje>());

                if (handlekurver.get(monsterLagtTilIHandlekurv.getKundenavn()).get(monsterLagtTilIHandlekurv.getMonsternavn()) == null)
                    handlekurver.get(monsterLagtTilIHandlekurv.getKundenavn()).put(monsterLagtTilIHandlekurv.getMonsternavn(), new Ordrelinje(monsterLagtTilIHandlekurv.getMonsternavn(), monsterLagtTilIHandlekurv.getPris()));

                handlekurver.get(monsterLagtTilIHandlekurv.getKundenavn()).get(monsterLagtTilIHandlekurv.getMonsternavn()).addMonster();
            }
        });

        eventHandlers.put(MonsterFjernetFraHandlekurv.class, new Procedure<HandlekurvEvt>() {
            public void apply(HandlekurvEvt evt) throws Exception {
                MonsterFjernetFraHandlekurv monsterFjernetFraHandlekurv = (MonsterFjernetFraHandlekurv) evt;
                Ordrelinje ordrelinje = handlekurver.get(monsterFjernetFraHandlekurv.getKundenavn()).get(monsterFjernetFraHandlekurv.getMonsternavn());

                ordrelinje.fjernMonster();
                if (ordrelinje.getAntall() == 0)
                    handlekurver.get(monsterFjernetFraHandlekurv.getKundenavn()).remove(ordrelinje.getMonsternavn());
            }
        });

        eventHandlers.put(OrdreBekreftet.class, new Procedure<HandlekurvEvt>() {
            public void apply(HandlekurvEvt evt) throws Exception {
                OrdreBekreftet ordreBekreftet = (OrdreBekreftet) evt;
                for (Ordrelinje ordrelinje  : handlekurver.get(ordreBekreftet.getKundenavn()).values()){
                    List<Ordrelinje> ordrelinjer = new ArrayList<>();
                    ordrelinjer.add(ordrelinje);

                    if (bekreftedeOrdrer.get(ordreBekreftet.getKundenavn()) == null)
                        bekreftedeOrdrer.put(ordreBekreftet.getKundenavn(), new ArrayList<BekreftetOrdre>());

                    bekreftedeOrdrer.get(ordreBekreftet.getKundenavn()).add(new BekreftetOrdre(ordrelinjer));
                }
                handlekurver.remove(ordreBekreftet.getKundenavn());
            }
        });
    }
}
