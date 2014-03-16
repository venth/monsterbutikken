package no.borber.monsterShop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public abstract class MonsterShopController {

    @Resource
    private HttpServletRequest httpRequest;

    protected String getCurrentCustomer() {
        return (String)httpRequest.getSession().getAttribute("customerName");
    }

    protected void setCurrentCustomer(String customerName) {
        httpRequest.getSession().setAttribute("customerName", customerName);
    }

    protected void logoutCurrentCustomer() {
        httpRequest.getSession().removeAttribute("customerName");
    }

    protected String getCurrentBasket() {
        return (String)httpRequest.getSession().getAttribute("basketId");
    }

    protected void setCurrentBasket(String basketId) {
        httpRequest.getSession().setAttribute("basketId", basketId);
    }

}
