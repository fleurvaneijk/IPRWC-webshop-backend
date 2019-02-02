package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import nl.hsleiden.View;
import nl.hsleiden.model.Cart;
import nl.hsleiden.service.CartService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author Fleur van Eijk
 */
@Singleton
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {

    CartService cartService;

    @Inject
    public CartResource(CartService cartService){
        this.cartService = cartService;
    }

    @POST
    @Path("/addProductToCart")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void addToCart(@Valid Cart cart){
        cartService.addToCart(cart);

    }

    @POST
    @Path("/getItemFromCart")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Cart getItemFromCart(@Valid Cart cart) {
        return cartService.getItemFromCart(cart);
    }

    @POST
    @Path("/deleteProductFromCart")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteFromCart(@Valid Cart cart){
        cartService.deleteFromCart(cart);

    }

    @GET
    @Path("/{email}")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    public Collection<Cart> getCart(@PathParam("email") String userEmail){
        return cartService.getCart(userEmail);
    }
}
