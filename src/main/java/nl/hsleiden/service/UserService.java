package nl.hsleiden.service;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Singleton;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class UserService extends BaseService<User> {

    private final UserDAO dao;
    
    @Inject
    public UserService(UserDAO dao)
    {
        this.dao = dao;
    }
    
    public Collection<User> getAll()
    {
        return dao.getAll();
    }
    
    public User getUser(String email)
    {
        return requireResult(dao.getUser(email));
    }
    
    public void add(User user) {
        user.setRole("GUEST");
        dao.add(user);
    }
    
    public void changePassword(User user) {
        // Controleren of deze gebruiker wel bestaat
        if(getUser(user.getEmail()) != null){
            dao.changePassword(user);
        }
    }
    
    public void delete(String email) {
        // Controleren of deze gebruiker wel bestaat
        User user = getUser(email);
        
        dao.delete(email);
    }
}
