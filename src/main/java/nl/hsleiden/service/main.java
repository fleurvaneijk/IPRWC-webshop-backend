package nl.hsleiden.service;

import nl.hsleiden.persistence.DatabaseConnection;
import nl.hsleiden.persistence.UserDAO;

public class main {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        UserDAO userdao = new UserDAO(databaseConnection);
        userdao.getAll();
    }
}
