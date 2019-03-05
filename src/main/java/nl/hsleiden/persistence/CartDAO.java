package nl.hsleiden.persistence;

import nl.hsleiden.DatabaseConnection;
import nl.hsleiden.model.CartItem;
import nl.hsleiden.model.DatabaseInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Fleur van Eijk
 */
public class CartDAO {
    private final DatabaseConnection database;
    private ArrayList<CartItem> cart;

    public CartDAO(){
        this.database = new DatabaseConnection();
        this.cart = new ArrayList<CartItem>();
    }

    public List<CartItem> getCart(String userEmail) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "SELECT " + DatabaseInfo.cartColumn.productId + ", " + DatabaseInfo.cartColumn.amount +
                    " FROM " + DatabaseInfo.cartTable + " WHERE " + DatabaseInfo.cartColumn.userEmail + " = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, userEmail);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                CartItem cartItem = new CartItem(userEmail, resultSet.getInt(DatabaseInfo.cartColumn.productId),
                        resultSet.getInt(DatabaseInfo.cartColumn.amount));
                cart.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                Objects.requireNonNull(resultSet).close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cart;
    }

    public void addToCart(String userEmail, int productId) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "INSERT INTO " + DatabaseInfo.cartTable + " VALUES (?,?,1)";
            statement = connection.prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Boolean checkCartForProduct(String userEmail, int productId){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "SELECT * FROM " + DatabaseInfo.cartTable + " WHERE " + DatabaseInfo.cartColumn.userEmail + " = ? AND " + DatabaseInfo.cartColumn.productId + " = ?;";
            statement = connection.prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setInt(2, productId);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                Objects.requireNonNull(resultSet).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void changeAmount(int difference, String userEmail, int productId){
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "UPDATE " + DatabaseInfo.cartTable + " SET " + DatabaseInfo.cartColumn.amount + " = " + DatabaseInfo.cartColumn.amount + " + ?" + " WHERE " +
                    DatabaseInfo.cartColumn.userEmail + " = ? AND " + DatabaseInfo.cartColumn.productId + " = ?;";

            statement = connection.prepareStatement(query);
            statement.setInt(1, difference);
            statement.setString(2, userEmail);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteFromCart(String userEmail, int productId) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "DELETE FROM " + DatabaseInfo.cartTable + " WHERE " + DatabaseInfo.cartColumn.userEmail + " = ? AND " + DatabaseInfo.cartColumn.productId + " = ?;";
            statement = database.getConnection().prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
