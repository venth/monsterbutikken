package no.borber.monsterShop.basket;

import akka.actor.ActorRef;
import no.borber.monsterShop.monsterTypes.PriceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketApplicationService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private PriceDomainService priceDomainService;

    public void createBasket(String basketId, String customerId){
        ActorRef basket = basketRepository.createBasket(basketId);
        basket.tell(new CreateBasket(basketId, customerId), null);
    }

    public void addMonsterToBasket(String basketId, String monsterType) {
        ActorRef basket = basketRepository.getById(basketId);
        basket.tell(new AddMonsterToBasket(monsterType, priceDomainService.gePrice(monsterType)), null);
    }

    public void removeMonsterFromBasket(String basketId, String monsterType) {
        ActorRef basket = basketRepository.getById(basketId);
        basket.tell(new RemoveMonsterFromBasket(monsterType), null);
    }



}
