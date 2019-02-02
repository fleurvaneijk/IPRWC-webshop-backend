package nl.hsleiden.service;

import nl.hsleiden.model.CartItem;
import nl.hsleiden.persistence.CartDAO;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Fleur van Eijk
 */
public class CartService {

    private final CartDAO dao;

    @Inject
    public CartService(CartDAO dao)
    {
        this.dao = dao;
    }

    public Collection<CartItem> getCart(String userEmail)
    {
        return dao.getCart(userEmail);
    }

    public void addToCart(CartItem cartItem) {
        if(dao.checkCartForProduct(cartItem.getUserEmail(), cartItem.getProductId())){
            dao.changeAmount(cartItem.getAmount(), cartItem.getUserEmail(), cartItem.getProductId());
        }else{
            dao.addToCart(cartItem.getUserEmail(), cartItem.getProductId());
        }
    }

    public void deleteFromCart(CartItem cartItem) {
        if(dao.checkCartForProduct(cartItem.getUserEmail(), cartItem.getProductId())){
            if(cartItem.getAmount() == 1){
                dao.deleteFromCart(cartItem.getUserEmail(), cartItem.getProductId());
            }else {
                dao.changeAmount(-1, cartItem.getUserEmail(), cartItem.getProductId());
            }
        }
    }
}
