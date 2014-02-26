package no.borber.monsterShop.basket;

import akka.actor.ActorRef;
import akka.persistence.Update;
import no.borber.monsterShop.MonsterShopController;
import no.borber.monsterShop.monsterTypes.MonsterTypesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import javax.annotation.Resource;
import java.util.Map;

import static akka.pattern.Patterns.ask;


@Controller
public class BasketController extends MonsterShopController{

    @Resource(name="basketProjection")
    ActorRef basketProjection;

    @Resource(name="basketHandler")
    ActorRef basketHandler;

    public static final Logger log = LoggerFactory.getLogger(BasketController.class);

    /**
     * Gets the current state of a customers basket
     *
     * @return Map of String monsterType og basketItem for the applicable monster type.
     */
    @RequestMapping(value = "/basket/",  method=RequestMethod.GET)
    @ResponseBody()
    public Map<String, BasketLineItem> getBasket(){
        log.info("getting basket");
        try {
            basketProjection.tell(Update.create(true), null);
            log.info("update completed");
            return (Map<String, BasketLineItem>) Await.result(ask(basketProjection, new GetBasket(getCurrentCustomer()), 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("error while fetching basket", e);
        }
    }

    /**
     * Removes a monster from the customers basket. If the resulting number of monsters reaches 0, the basket item is
     * removed.
     *
     * @param monstertype name of the monstertype to be removed
     */
    @RequestMapping(value = "/basket/remove/{monstertype}",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String monstertype){
        try {
            Await.result(ask(basketHandler, new RemoveMonsterFromBasket(getCurrentCustomer(), monstertype), 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("error while removing monster from basket", e);
        }
    }

    /**
     * Adds a new monster of a specified type to the customers basket. If there is an existing basket item the number
     * of monsters is incremented, otherwise a new order baslet item is created.
     *
     * @param monstertype name of the monstertype to be added
     */
    @RequestMapping(value = "/basket/add/{monstertype}",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void add(@PathVariable String monstertype){
        log.info("adding monster basket");
        try {
            Await.result(ask(basketHandler, new AddMonsterToBasket(getCurrentCustomer(), monstertype, MonsterTypesRepo.getMonsterType(monstertype).getPrice()), 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("error while removing monster from basket", e);
        }
    }

    /**
     * Calculates the sum of (price * number) for all items in the basket.
     */
    @RequestMapping(value = "/basket/sum",  method=RequestMethod.GET)
    @ResponseBody
    public BasketSum sum(){
        try {
            basketProjection.tell(Update.create(true), null);
            Map<String, BasketLineItem> handlekurv = (Map<String, BasketLineItem>) Await.result(ask(basketProjection, new GetBasket(getCurrentCustomer()), 3000), Duration.create("3 seconds"));

            double sum = 0;
            for (BasketLineItem ordre : handlekurv.values())
                sum = sum + (MonsterTypesRepo.getMonsterType(ordre.getMonsterType()).getPrice() * ordre.getNumber());
            return new BasketSum(sum);
        } catch (Exception e) {
            throw new RuntimeException("feil under kalkulering av handlekurv sum", e);
        }
    }

}
