package no.borber.monsterShop.orders;

import akka.actor.ActorRef;
import akka.persistence.Update;
import no.borber.monsterShop.GetOrders;
import no.borber.monsterShop.MonsterShopController;
import no.borber.monsterShop.basket.AddMonsterToBasket;
import no.borber.monsterShop.monsterTypes.MonsterTypesRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import javax.annotation.Resource;
import java.util.Map;

import static akka.pattern.Patterns.ask;


@Controller
public class OrderController extends MonsterShopController {


    @Resource(name="basketProjection")
    ActorRef basketProjection;

    @Resource(name="orderProjection")
    ActorRef orderProjection;

    //@Resource(name="orderHandler")
    ActorRef orderHandler;

    /**
     * Gets the current customers orderProjection
     *
     * @return Map of orderProjection, where the key is the order-id and the value an order object.
     */
    @RequestMapping(value = "/orders",  method=RequestMethod.GET)
    @ResponseBody()
    public Map<String, Order> getOrderProjection(){
        try {
            orderProjection.tell(Update.create(true), null);
            return (Map<String, Order>) Await.result(ask(orderProjection, new GetOrders(getCurrentCustomer()), 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching orderProjection for customer " + getCurrentCustomer(), e);
        }
    }

    /**
     * Gets a single order
     *
     * @param orderId identifier for the order to be retrieved
     */
    @RequestMapping(value = "/orders/{orderId}",  method=RequestMethod.GET)
    @ResponseBody()
    public Order getOrder(@PathVariable String orderId){
        try {
            orderProjection.tell(Update.create(true), null);
            return (Order) Await.result(ask(orderProjection, new GetOrder(getCurrentCustomer(), orderId), 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching order " + orderId +  " for customer " + getCurrentCustomer(), e);
        }
    }

    /**
     * Places a new order for the current customer based on the contents of the customers basket
     */
    @RequestMapping(value = "/orders/placeOrder",  method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void placeOrder(){
        try {
           // Await.result(ask(orderHandler, new PlaceOrder(getCurrentCustomer()), 3000), Duration.create("3 seconds"));
        } catch (Exception e) {
            throw new RuntimeException("error while placing order from basket", e);
        }
    }
}


