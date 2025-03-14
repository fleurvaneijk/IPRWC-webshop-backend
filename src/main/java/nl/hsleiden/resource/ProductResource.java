package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import nl.hsleiden.View;
import nl.hsleiden.model.Product;
import nl.hsleiden.service.ProductService;
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
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;

    @Inject
    public ProductResource(ProductService service)
    {
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Product> retrieveAll()
    {
        return service.getAll();
    }

    @GET
    @Path("/{productId}")
    @JsonView(View.Public.class)
    public Product retrieve(@PathParam("productId") int productId) {
        return service.getProduct(productId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    @RolesAllowed("ADMIN")
    public void create(Product product) {
        service.add(product);
    }

    @POST
    @Path("/{productId}")
//    @RolesAllowed("ADMIN")
    public void update(@PathParam("productId") int id, @Valid Product product) {
        service.update(id, product);
    }

    @DELETE
    @Path("/delete/{productId}")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("productId") int productId) {
        service.delete(productId);
    }

//    @PUT
//    @Path("/changeTitle/{productId}")
//    @RolesAllowed("ADMIN")
//    public void changeTitle(@PathParam("productId") int productId, String title) {
//        service.changeTitle(productId, title);
//    }
//
//    @PUT
//    @Path("/changeDescription/{productId}")
//    @RolesAllowed("ADMIN")
//    public void changeDescription(@PathParam("productId") int productId, String description) {
//        service.changeDescription(productId, description);
//    }
//
//    @PUT
//    @Path("/changeImage/{productId}")
//    @RolesAllowed("ADMIN")
//    public void changeImage(@PathParam("productId") int productId, String image) {
//        service.changeImage(productId, image);
//    }
//
//    @PUT
//    @Path("/changePrice/{productId}")
//    @RolesAllowed("ADMIN")
//    public void changePrice(@PathParam("productId") int productId, double price) {
//        service.changePrice(productId, price);
//    }
}
