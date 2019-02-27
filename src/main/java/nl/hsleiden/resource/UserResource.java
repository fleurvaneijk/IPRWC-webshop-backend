package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.hsleiden.View;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

/**
 * Meer informatie over resources:
 *  https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources
 * 
 * @author Peter van Vliet
 */
@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource
{
    private final UserService service;
    
    @Inject
    public UserResource(UserService service)
    {
        this.service = service;
    }
    
    @GET
    @JsonView(View.Public.class)
    public Collection<User> retrieveAll() {
        return service.getAll();
    }
    
    @GET
    @Path("/{email}")
    @JsonView(View.Public.class)
    public User retrieve(@PathParam("email") String email) {
        return service.getUser(email);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(@Valid User user) {
        service.add(user);
    }
    
    @DELETE
    @Path("/{email}")
    @RolesAllowed({"ADMIN", "GUEST"})
    public void delete(@PathParam("email") String email)
    {
        service.delete(email);
    }

    @PUT
    @Path("/{email}")
    @RolesAllowed({"ADMIN", "GUEST"})
    public void update(@PathParam("email") String email, @Valid User newUser) {
        service.update(email, newUser);
    }
    
    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User authenticate(@Auth User authenticator)
    {
        return authenticator;
    }
}
