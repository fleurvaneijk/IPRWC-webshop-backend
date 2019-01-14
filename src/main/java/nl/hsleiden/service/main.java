package nl.hsleiden.service;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.DatabaseConnection;
import nl.hsleiden.persistence.UserDAO;

public class main {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        UserDAO userdao = new UserDAO(databaseConnection);
//        userdao.getAll();

//        userdao.add(new User("test@test.nl", "test", "wachtwoord", "GUEST"));
//        userdao.delete("test@test.nl");
        userdao.getByEmail("fleur.vaneijk99@gmail.com");

//        userdao.getAll();

    }
}
