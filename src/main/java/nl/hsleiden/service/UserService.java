package nl.hsleiden.service;

import java.util.ArrayList;
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
public class UserService {

    private final UserDAO dao;
    
    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }
    
    public ArrayList<User> getAll() {
        return dao.getAll();
    }
    
    public User getUser(String email)
    {
        return dao.getUser(email);
    }
    
    public void add(User user) {
        user.setRole("GUEST");
        dao.add(user);
    }

    public void addAdmin(User admin) {
        admin.setRole("ADMIN");
        dao.add(admin);
    }

    public void update(String email, User newUser) {
        if(getUser(email) != null){
            dao.update(email, newUser);
        }
    }
    
    public void delete(String email) {
        if(getUser(email) != null){
            dao.delete(email);
        }
    }
}
