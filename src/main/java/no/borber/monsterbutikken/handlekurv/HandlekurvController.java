package no.borber.monsterbutikken.handlekurv;

import akka.actor.ActorRef;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import javax.annotation.Resource;

import static akka.pattern.Patterns.ask;

public class HandlekurvController {

    @Resource(name="handlekurv")
    ActorRef handlekurv;

    @RequestMapping(value = "/handlekurv/{bruker}",  method=RequestMethod.GET)
    public void getHandlekurv(@PathVariable String bruker){
        try {
            Await.result(ask(handlekurv, bruker, 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @RequestMapping(value = "/handlekurv/{bruker}/fjern/{monsterNavn}",  method=RequestMethod.POST)
    public void fjernMonster(@PathVariable String bruker, @PathVariable String monsterNavn){
        handlekurv.tell(new FjernMonsterFraHandlekurv(bruker, monsterNavn), null);
    }

    @RequestMapping(value = "/handlekurv/{bruker}/leggTil/{monsterNavn}",  method=RequestMethod.POST)
    public void leggTilMonster(@PathVariable String bruker, @PathVariable String monsterNavn){
        handlekurv.tell(new LeggMonsterIHandlekurv(bruker, monsterNavn), null);
    }
}
