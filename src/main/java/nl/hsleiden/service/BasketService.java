package nl.hsleiden.service;

import nl.hsleiden.model.Basket;
import nl.hsleiden.persistence.BasketDAO;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Fleur van Eijk
 */
public class BasketService {

    private final BasketDAO dao;

    @Inject
    public BasketService(BasketDAO dao)
    {
        this.dao = dao;
    }

    public Collection<Basket> getBasket(String userEmail)
    {
        return dao.getBasket(userEmail);
    }

    public void addToBasket(Basket basket) {
        if(dao.checkBasketForProduct(basket.getUserEmail(), basket.getProductId()) == true){
            dao.changeAmount(basket.getAmount(), basket.getUserEmail(), basket.getProductId());
        }else{
            dao.addToBasket(basket.getUserEmail(), basket.getProductId());
        }
    }

    public Basket getItemFromBasket(Basket basket){
        return dao.getItemFromBasket(basket);
    }

    public void deleteFromBasket(Basket basket) {
        if(dao.checkBasketForProduct(basket.getUserEmail(), basket.getProductId())){
            if(getItemFromBasket(basket).getAmount() == 1){
                dao.deleteFromBasket(basket.getUserEmail(), basket.getProductId());
            }else {
                dao.changeAmount(-1, basket.getUserEmail(), basket.getProductId());
            }
        }
    }
}
