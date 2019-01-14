package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import nl.hsleiden.View;
import nl.hsleiden.model.Basket;
import nl.hsleiden.service.BasketService;

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
@Path("/baskets")
@Produces(MediaType.APPLICATION_JSON)
public class BasketResource {

    BasketService basketService;

    @Inject
    public BasketResource(BasketService basketService){
        this.basketService = basketService;
    }

    @POST
    @Path("/addProductToBasket")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void addToBasket(@Valid Basket basket){
        basketService.addToBasket(basket);

    }

    @POST
    @Path("/getItemFromBasket")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Basket getItemFromBasket(@Valid Basket basket) {
        return basketService.getItemFromBasket(basket);
    }

    @POST
    @Path("/deleteProductFromBasket")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteFromBasket(@Valid Basket basket){
        basketService.deleteFromBasket(basket);

    }

    @GET
    @Path("/{email}")
    @JsonView(View.Public.class)
    @RolesAllowed({"'ADMIN'", "'GUEST'"})
    public Collection<Basket> getBasket(@PathParam("email") String userEmail){
        return basketService.getBasket(userEmail);
    }
}
