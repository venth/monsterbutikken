package no.borber.monsterbutikken.handlekurv;

import akka.actor.ActorRef;
import no.borber.monsterbutikken.monstre.MonsterRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import javax.annotation.Resource;

import java.util.Map;

import static akka.pattern.Patterns.ask;

public class HandlekurvController {

    @Resource(name="handlekurv")
    ActorRef handlekurvStore;

    @RequestMapping(value = "/handlekurv/{bruker}",  method=RequestMethod.GET)
    public Map<String, MonsterOrdre> getHandlekurv(@PathVariable String bruker){
        try {
            return (Map<String, MonsterOrdre>) Await.result(ask(handlekurvStore, bruker, 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("feil under henting av handlekurv", e);
        }
    }

    @RequestMapping(value = "/handlekurv/{bruker}/fjern/{monsterNavn}",  method=RequestMethod.POST)
    public void fjernMonster(@PathVariable String bruker, @PathVariable String monsterNavn){
        handlekurvStore.tell(new FjernMonsterFraHandlekurv(bruker, monsterNavn), null);
    }

    @RequestMapping(value = "/handlekurv/{bruker}/leggTil/{monsterNavn}",  method=RequestMethod.POST)
    public void leggTilMonster(@PathVariable String bruker, @PathVariable String monsterNavn){
        handlekurvStore.tell(new LeggMonsterIHandlekurv(bruker, monsterNavn), null);
    }

    @RequestMapping(value = "/handlekurv/{bruker}/bekreftOrdre",  method=RequestMethod.POST)
    public void bekreftOrdre(@PathVariable String bruker){
        handlekurvStore.tell(new BekreftOrdre(bruker), null);
    }

    @RequestMapping(value = "/handlekurv/{bruker}/handlekurvSum",  method=RequestMethod.GET)
    public HandlekurvSum handlekurvSum(@PathVariable String bruker){
        try {
            Map<String, MonsterOrdre> handlekurv = (Map<String, MonsterOrdre>) Await.result(ask(handlekurvStore, bruker, 3000), Duration.create("3 seconds"));

            double sum = 0;
            for (MonsterOrdre ordre : handlekurv.values())
                sum = sum + (MonsterRepo.getMonster(ordre.getMonsterNavn()).getPris() * ordre.getAntall());
            return new HandlekurvSum(sum);
        } catch (Exception e) {
            throw new RuntimeException("feil under kalkulering av handlekurv sum", e);
        }
    }
}
