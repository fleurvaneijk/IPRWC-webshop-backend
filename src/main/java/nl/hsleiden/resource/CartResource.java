package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import nl.hsleiden.View;
import nl.hsleiden.model.CartItem;
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
    public void addToCart(@Valid CartItem cartItem){
        cartService.addToCart(cartItem);

    }

    @POST
    @Path("/getItemFromCart")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public CartItem getItemFromCart(@Valid CartItem cartItem) {
        return cartService.getItemFromCart(cartItem);
    }

    @POST
    @Path("/deleteProductFromCart")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteFromCart(@Valid CartItem cartItem){
        cartService.deleteFromCart(cartItem);

    }

    @GET
    @Path("/{email}")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    public Collection<CartItem> getCart(@PathParam("email") String userEmail){
        return cartService.getCart(userEmail);
    }
}
