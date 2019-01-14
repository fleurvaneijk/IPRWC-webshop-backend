package nl.hsleiden.persistence;

import nl.hsleiden.model.Basket;
import nl.hsleiden.model.DatabaseInfo;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fleur van Eijk
 */
public class BasketDAO {
    private final DatabaseConnection database;
    private List<Basket> baskets;
    private ResultSet resultSet = null;

    public BasketDAO(){
        this.database = new DatabaseConnection();
        this.baskets = new ArrayList<>();
    }

    public List<Basket> getBasket(String userEmail) {
        try {
            String query = "SELECT " + DatabaseInfo.basketColumnNames.productId + ", " + DatabaseInfo.basketColumnNames.amount +
                    " FROM " + DatabaseInfo.basketTableName + " WHERE " + DatabaseInfo.basketColumnNames.userEmail + " = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, userEmail);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Basket basket = new Basket(userEmail, resultSet.getInt(DatabaseInfo.basketColumnNames.productId),
                        resultSet.getInt(DatabaseInfo.basketColumnNames.amount));
                baskets.add(basket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return baskets;
    }

    public void addToBasket(String userEmail, int productId) {
        try {
            String query = "INSERT INTO " + DatabaseInfo.basketTableName + " VALUES (?,?,1)";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Boolean checkBasketForProduct(String userEmail, int productId){
        try {
            String query = "SELECT * FROM " + DatabaseInfo.basketTableName + " WHERE " + DatabaseInfo.basketColumnNames.userEmail + " = ? AND " + DatabaseInfo.basketColumnNames.productId + " = ?;";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setInt(2, productId);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void changeAmount(int difference, String userEmail, int productId){
        try {
            String query = "UPDATE " + DatabaseInfo.basketTableName + " SET " + DatabaseInfo.basketColumnNames.amount + " = " + DatabaseInfo.basketColumnNames.amount + " + ?" + " WHERE " +
                    DatabaseInfo.basketColumnNames.userEmail + " = ? AND " + DatabaseInfo.basketColumnNames.productId + " = ?;";

            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setInt(1, difference);
            statement.setString(2, userEmail);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Basket getItemFromBasket (Basket basket) {
        try {

            String query = "SELECT  * FROM " + DatabaseInfo.basketTableName + " WHERE " + DatabaseInfo.basketColumnNames.userEmail + " = ? AND "
                    + DatabaseInfo.basketColumnNames.productId + " =?;";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, basket.getUserEmail());
            statement.setInt(2, basket.getProductId());
            resultSet = statement.executeQuery();
            resultSet.next();
            return new Basket(resultSet.getString(DatabaseInfo.basketColumnNames.userEmail), resultSet.getInt(DatabaseInfo.basketColumnNames.productId),
                                resultSet.getInt(DatabaseInfo.basketColumnNames.amount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteFromBasket(String userEmail, int productId) {
        try {
            String query = "DELETE FROM " + DatabaseInfo.basketTableName + " WHERE " + DatabaseInfo.basketColumnNames.userEmail + " = ? AND " + DatabaseInfo.basketColumnNames.productId + " = ?;";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
