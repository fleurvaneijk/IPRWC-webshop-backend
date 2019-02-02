package nl.hsleiden.persistence;

import nl.hsleiden.model.Cart;
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
public class CartDAO {
    private final DatabaseConnection database;
    private List<Cart> carts;
    private ResultSet resultSet = null;

    public CartDAO(){
        this.database = new DatabaseConnection();
        this.carts = new ArrayList<>();
    }

    public List<Cart> getCart(String userEmail) {
        try {
            String query = "SELECT " + DatabaseInfo.cartColumnNames.productId + ", " + DatabaseInfo.cartColumnNames.amount +
                    " FROM " + DatabaseInfo.cartTableName + " WHERE " + DatabaseInfo.cartColumnNames.userEmail + " = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, userEmail);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Cart cart = new Cart(userEmail, resultSet.getInt(DatabaseInfo.cartColumnNames.productId),
                        resultSet.getInt(DatabaseInfo.cartColumnNames.amount));
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public void addToCart(String userEmail, int productId) {
        try {
            String query = "INSERT INTO " + DatabaseInfo.cartTableName + " VALUES (?,?,1)";
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

    public Boolean checkCartForProduct(String userEmail, int productId){
        try {
            String query = "SELECT * FROM " + DatabaseInfo.cartTableName + " WHERE " + DatabaseInfo.cartColumnNames.userEmail + " = ? AND " + DatabaseInfo.cartColumnNames.productId + " = ?;";
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
            String query = "UPDATE " + DatabaseInfo.cartTableName + " SET " + DatabaseInfo.cartColumnNames.amount + " = " + DatabaseInfo.cartColumnNames.amount + " + ?" + " WHERE " +
                    DatabaseInfo.cartColumnNames.userEmail + " = ? AND " + DatabaseInfo.cartColumnNames.productId + " = ?;";

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

    public Cart getItemFromCart (Cart cart) {
        try {

            String query = "SELECT  * FROM " + DatabaseInfo.cartTableName + " WHERE " + DatabaseInfo.cartColumnNames.userEmail + " = ? AND "
                    + DatabaseInfo.cartColumnNames.productId + " =?;";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, cart.getUserEmail());
            statement.setInt(2, cart.getProductId());
            resultSet = statement.executeQuery();
            resultSet.next();
            return new Cart(resultSet.getString(DatabaseInfo.cartColumnNames.userEmail), resultSet.getInt(DatabaseInfo.cartColumnNames.productId),
                                resultSet.getInt(DatabaseInfo.cartColumnNames.amount));
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

    public void deleteFromCart(String userEmail, int productId) {
        try {
            String query = "DELETE FROM " + DatabaseInfo.cartTableName + " WHERE " + DatabaseInfo.cartColumnNames.userEmail + " = ? AND " + DatabaseInfo.cartColumnNames.productId + " = ?;";
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
