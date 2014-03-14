package no.borber.monsterShop.basket;

import no.borber.monsterShop.MonsterShopController;
import no.borber.monsterShop.eventStore.monsterStore.Repo;
import no.borber.monsterShop.monsterTypes.MonsterTypesRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@Controller
public class BasketController extends MonsterShopController{

    /**
     * Gets the current state of a customers basket
     *
     * @return Map of String monsterType og basketItem for the applicable monster type.
     */
    @RequestMapping(value = "/basket/",  method=RequestMethod.GET)
    @ResponseBody()
    public Map<String, BasketItem> getBasket(){
        return Repo.getBasketProjection().getBasket(getCurrentBasketId());
    }

    /**
     * Adds a new monster of a specified type to the customers basket. If there is an existing basket item the number
     * of monsters is incremented, otherwise a new order basket item is created.
     *
     * @param monstertype name of the monstertype to be added
     */
    @RequestMapping(value = "/basket/{monstertype}",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void add(@PathVariable String monstertype){
        Basket basket;
        if (getCurrentBasketId() == null) {
            String aggregateId = UUID.randomUUID().toString();
            basket = Repo.createBasket(aggregateId);
            setCurrentBasket(aggregateId);
        }
        else
            basket = Repo.getBasket(getCurrentBasketId());

        basket.handleCommand(new AddMonster(monstertype, MonsterTypesRepo.getMonsterType(monstertype).getPrice()));

    }

    /**
     * Removes a monster from the customers basket. If the resulting number of monsters reaches 0, the basket item is
     * removed.
     *
     * @param monstertype name of the monstertype to be removed
     */
    @RequestMapping(value = "/basket/{monstertype}",  method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String monstertype){
        Repo.getBasket(getCurrentBasketId()).handleCommand(new RemoveMonster(monstertype));

    }

    /**
     * Calculates the sum of (price * number) for all items in the basket.
     */
    @RequestMapping(value = "/basket/sum",  method=RequestMethod.GET)
    @ResponseBody
    public BasketSum sum(){
        if (getCurrentBasketId() == null)
            return null;
        else
            return new BasketSum(Repo.getBasketProjection().getBasketSum(getCurrentBasketId()));
    }

}
