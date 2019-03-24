package nl.hsleiden.service;

import java.util.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import javax.inject.Inject;
import javax.inject.Singleton;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class AuthenticationService implements Authenticator<BasicCredentials, User>, Authorizer<User>
{
    private final UserDAO userDAO;
    
    @Inject
    public AuthenticationService(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        User user = userDAO.getUser(credentials.getUsername());
        
        if (user != null && this.checkPassword(credentials.getPassword(), user.getPassword())) {
            return Optional.of(user);
        }
        
        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String roleName)
    {
        return user.hasRole(roleName);
    }

    private boolean checkPassword(String plaintext, String hashedPassword){
        boolean corresponds = false;
        if (BCrypt.checkpw(plaintext, hashedPassword))
            corresponds = true;
        return corresponds;
    }
}
