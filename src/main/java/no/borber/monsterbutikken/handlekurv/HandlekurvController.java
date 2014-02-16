package no.borber.monsterbutikken.handlekurv;

import akka.actor.ActorRef;
import no.borber.monsterbutikken.monstre.MonsterRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static akka.pattern.Patterns.ask;


@Controller
public class HandlekurvController {

    @Resource
    private HttpServletRequest httpRequest;

    @Resource(name="handlekurv")
    ActorRef handlekurvStore;

    @RequestMapping(value = "/handlekurv/",  method=RequestMethod.GET)
    @ResponseBody()
    public Map<String, MonsterOrdre> getHandlekurv(){
        try {
            String innloggetBruker = getInnloggetBruker();
            return (Map<String, MonsterOrdre>) Await.result(ask(handlekurvStore, innloggetBruker, 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("feil under henting av handlekurv", e);
        }
    }

    @RequestMapping(value = "/handlekurv/fjern/{monsterNavn}",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void fjernMonster( @PathVariable String monsterNavn){
        handlekurvStore.tell(new FjernMonsterFraHandlekurv(getInnloggetBruker(), monsterNavn), null);
    }

    @RequestMapping(value = "/handlekurv/leggTil/{monsterNavn}",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void leggTilMonster(@PathVariable String monsterNavn){
        handlekurvStore.tell(new LeggMonsterIHandlekurv(getInnloggetBruker(), monsterNavn, MonsterRepo.getMonster(monsterNavn).getPris()), null);
    }

    @RequestMapping(value = "/handlekurv/bekreftOrdre",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void bekreftOrdre(){
        handlekurvStore.tell(new BekreftOrdre(getInnloggetBruker()), null);
    }

    @RequestMapping(value = "/handlekurv/sum",  method=RequestMethod.GET)
    @ResponseBody
    public HandlekurvSum handlekurvSum(){
        try {
            Map<String, MonsterOrdre> handlekurv = (Map<String, MonsterOrdre>) Await.result(ask(handlekurvStore, getInnloggetBruker(), 3000), Duration.create("3 seconds"));

            double sum = 0;
            for (MonsterOrdre ordre : handlekurv.values())
                sum = sum + (MonsterRepo.getMonster(ordre.getMonsternavn()).getPris() * ordre.getAntall());
            return new HandlekurvSum(sum);
        } catch (Exception e) {
            throw new RuntimeException("feil under kalkulering av handlekurv sum", e);
        }
    }

    private String getInnloggetBruker() {
        return (String)httpRequest.getSession().getAttribute("bruker");
    }
}
