package no.borber.monsterbutikken.handlekurv;

import akka.actor.Props;
import akka.japi.Procedure;
import akka.persistence.UntypedEventsourcedProcessor;
import com.google.common.collect.ImmutableMap;
import no.borber.monsterbutikken.es.Cmd;
import no.borber.monsterbutikken.es.Evt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Handlekurv extends UntypedEventsourcedProcessor {

    public static final Logger log = LoggerFactory.getLogger(Handlekurv.class);

    private Map<String, Map<String, MonsterOrdre>> handlekurver = new HashMap<>();
    private Map<String, List<BekreftetOrdre>> bekreftedeOrdrer = new HashMap<>();
    private Map<Class, Procedure<HandlekurvEvt>> eventHandlers = new HashMap<>();

    public static Props mkProps() {
        return Props.create(Handlekurv.class);
    }

    public Handlekurv() {
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
            final String monsterNavn = ((LeggMonsterIHandlekurv) command).getMonsterNavn();
            final String bruker = ((LeggMonsterIHandlekurv) command).getBruker();
            final double pris = ((LeggMonsterIHandlekurv) command).getPris();

            if (monsterNavn != null && bruker != null) {
                persist(new MonsterLagtTilIHandlekurv(bruker, monsterNavn, pris), getHandler(MonsterLagtTilIHandlekurv.class));
            } else
                sender().tell("Invalid command!", self());
        } else if (command instanceof FjernMonsterFraHandlekurv){
            final String monsterNavn = ((FjernMonsterFraHandlekurv) command).getMonsterNavn();
            final String bruker = ((FjernMonsterFraHandlekurv) command).getBruker();

            if (monsterNavn != null && bruker != null) {
                persist(new MonsterFjernetFraHandlekurv(bruker, monsterNavn), getHandler(MonsterFjernetFraHandlekurv.class));
            } else
                sender().tell("Invalid command!", self());
        } else if (command instanceof BekreftOrdre){
            final String bruker = ((BekreftOrdre) command).getBruker();

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
                if (handlekurver.get(monsterLagtTilIHandlekurv.getBruker()) == null)
                    handlekurver.put(monsterLagtTilIHandlekurv.getBruker(), new HashMap<String, MonsterOrdre>());

                if (handlekurver.get(monsterLagtTilIHandlekurv.getBruker()).get(monsterLagtTilIHandlekurv.getMonsterNavn()) == null)
                    handlekurver.get(monsterLagtTilIHandlekurv.getBruker()).put(monsterLagtTilIHandlekurv.getMonsterNavn(), new MonsterOrdre(monsterLagtTilIHandlekurv.getMonsterNavn(), monsterLagtTilIHandlekurv.getPris()));

                handlekurver.get(monsterLagtTilIHandlekurv.getBruker()).get(monsterLagtTilIHandlekurv.getMonsterNavn()).addMonster();
            }
        });

        eventHandlers.put(MonsterFjernetFraHandlekurv.class, new Procedure<HandlekurvEvt>() {
            public void apply(HandlekurvEvt evt) throws Exception {
                MonsterFjernetFraHandlekurv monsterFjernetFraHandlekurv = (MonsterFjernetFraHandlekurv) evt;
                MonsterOrdre monsterOrdre = handlekurver.get(monsterFjernetFraHandlekurv.getBruker()).get(monsterFjernetFraHandlekurv.getMonsterNavn());

                monsterOrdre.fjernMonster();
                if (monsterOrdre.getAntall() == 0)
                    handlekurver.get(monsterFjernetFraHandlekurv.getBruker()).remove(monsterOrdre.getMonsternavn());
            }
        });

        eventHandlers.put(OrdreBekreftet.class, new Procedure<HandlekurvEvt>() {
            public void apply(HandlekurvEvt evt) throws Exception {
                OrdreBekreftet ordreBekreftet = (OrdreBekreftet) evt;
                for (MonsterOrdre ordrelinje  : handlekurver.get(ordreBekreftet.getBruker()).values()){
                    List<MonsterOrdre> ordrelinjer = new ArrayList<>();
                    ordrelinjer.add(ordrelinje);

                    if (bekreftedeOrdrer.get(ordreBekreftet.getBruker()) == null)
                        bekreftedeOrdrer.put(ordreBekreftet.getBruker(), new ArrayList<BekreftetOrdre>());

                    bekreftedeOrdrer.get(ordreBekreftet.getBruker()).add(new BekreftetOrdre(ordrelinjer));
                }
                handlekurver.remove(ordreBekreftet.getBruker());
            }
        });
    }
}
