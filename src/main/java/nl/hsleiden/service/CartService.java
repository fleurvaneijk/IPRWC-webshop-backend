package nl.hsleiden.service;

import nl.hsleiden.model.Cart;
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

    public Collection<Cart> getCart(String userEmail)
    {
        return dao.getCart(userEmail);
    }

    public void addToCart(Cart cart) {
        if(dao.checkCartForProduct(cart.getUserEmail(), cart.getProductId()) == true){
            dao.changeAmount(cart.getAmount(), cart.getUserEmail(), cart.getProductId());
        }else{
            dao.addToCart(cart.getUserEmail(), cart.getProductId());
        }
    }

    public Cart getItemFromCart(Cart cart){
        return dao.getItemFromCart(cart);
    }

    public void deleteFromCart(Cart cart) {
        if(dao.checkCartForProduct(cart.getUserEmail(), cart.getProductId())){
            if(getItemFromCart(cart).getAmount() == 1){
                dao.deleteFromCart(cart.getUserEmail(), cart.getProductId());
            }else {
                dao.changeAmount(-1, cart.getUserEmail(), cart.getProductId());
            }
        }
    }
}
