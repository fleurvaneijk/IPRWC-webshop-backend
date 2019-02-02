package nl.hsleiden.persistence;

import nl.hsleiden.DatabaseConnection;
import nl.hsleiden.model.DatabaseInfo;
import nl.hsleiden.model.Product;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DAO that gets data from the product table
 * @author Fleur van Eijk
 */
@Singleton
public class ProductDAO {
    ArrayList<Product> products;

    private final DatabaseConnection database;

    public ProductDAO(){
        this.database = new DatabaseConnection();
        this.products = new ArrayList<>();
    }

    public List<Product> getAll() {
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        Connection connection = null;
        products.clear();
        try {
            connection = database.getConnection();
            String query1 = "SELECT * FROM " + DatabaseInfo.productTableName;
            statement1 = connection.prepareStatement(query1);
            resultSet1 = statement1.executeQuery();

            while (resultSet1.next()) {
                String query2 = "SELECT "+ DatabaseInfo.imageColumnNames.image + " FROM " + DatabaseInfo.imageTableName + " WHERE product_id = " + resultSet1.getInt(DatabaseInfo.productColumnNames.id);
                statement2 = connection.prepareStatement(query2);
                resultSet2 = statement2.executeQuery();

                ArrayList<String> images = new ArrayList<String>();

                while(resultSet2.next()){
                    String image = resultSet2.getString(DatabaseInfo.imageColumnNames.image);
                    images.add(image);
                }

                Product product = new Product (resultSet1.getInt(DatabaseInfo.productColumnNames.id), resultSet1.getString(DatabaseInfo.productColumnNames.title),
                        resultSet1.getString(DatabaseInfo.productColumnNames.description), images, resultSet1.getDouble(DatabaseInfo.productColumnNames.price));

                System.out.println(product.getTitle() + "\t" +  product.getDescription() + "\t" + product.getPrice());
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                Objects.requireNonNull(statement1).close();
                Objects.requireNonNull(statement2).close();
                Objects.requireNonNull(resultSet1).close();
                Objects.requireNonNull(resultSet2).close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(products);
        return products;
    }

    public Product getProduct(int productId) {
        Product product = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        Connection connection = null;
        try{
            connection = database.getConnection();
            String query = "SELECT * FROM " + DatabaseInfo.productTableName + " WHERE  " + DatabaseInfo.productColumnNames.id + " = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setInt(1, productId);
            resultSet1 = statement1.executeQuery();
            resultSet1.next();

            String query2 = "SELECT "+ DatabaseInfo.imageColumnNames.image + " FROM " + DatabaseInfo.imageTableName + " WHERE product_id = " + resultSet1.getInt(DatabaseInfo.productColumnNames.id);
            statement2 = connection.prepareStatement(query2);
            resultSet2 = statement2.executeQuery();

            ArrayList<String> images = new ArrayList<String>();

            while(resultSet2.next()){
                String image = resultSet2.getString(DatabaseInfo.imageColumnNames.image);
                images.add(image);
            }
            product = new Product(resultSet1.getInt(DatabaseInfo.productColumnNames.id), resultSet1.getString(DatabaseInfo.productColumnNames.title),
                    resultSet1.getString(DatabaseInfo.productColumnNames.description), images, resultSet1.getDouble(DatabaseInfo.productColumnNames.price));
            return product;
        }catch(SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            try{
                Objects.requireNonNull(statement1).close();
                Objects.requireNonNull(statement2).close();
                Objects.requireNonNull(resultSet1).close();
                Objects.requireNonNull(resultSet2).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void add(Product product) {
        PreparedStatement statement = null;
        ResultSet lastId = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "INSERT INTO " + DatabaseInfo.productTableName + " VALUES(DEFAULT, ?, ?, ?);" +
                            "SELECT currval(pg_get_serial_sequence('" + DatabaseInfo.productTableName + "', '" + DatabaseInfo.productColumnNames.id + "'));";
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            lastId = statement.executeQuery();

            for (String image : product.getImages()) {
                String query2 = "INSERT INTO " + DatabaseInfo.imageTableName + " VALUES(?, ?)";
                statement = connection.prepareStatement(query2);
                statement.setInt(1, lastId.getInt("currval"));
                statement.setString(2, image);
                statement.executeUpdate();
            }

        }catch(Exception e){
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

    public void delete(int productId) {
        PreparedStatement statement = null;
        Connection connection = null;
        try{
            if(getProduct(productId) != null) {
                connection = database.getConnection();
                String query = "DELETE FROM " + DatabaseInfo.productTableName + " WHERE " + DatabaseInfo.productColumnNames.id + " =?;";
                statement = connection.prepareStatement(query);
                statement.setInt(1, productId);
                statement.executeUpdate();
            }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
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

    public void changeTitle(int productId, String title) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            if(getProduct(productId) != null) {
                connection = database.getConnection();
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.title + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                statement = connection.prepareStatement(query);
                statement.setString(1, title);
                statement.setInt(2, productId);
                statement.executeUpdate();
            }
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

    public void changeDescription(int productId, String description) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            if(getProduct(productId) != null) {
                connection = database.getConnection();
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.description + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                statement = connection.prepareStatement(query);
                statement.setString(1, description);
                statement.setInt(2, productId);
                statement.executeUpdate();
            }
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

    public void changePrice(int productId, Double price) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            if(getProduct(productId) != null) {
                connection = database.getConnection();
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.price + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                statement = connection.prepareStatement(query);
                statement.setDouble(1, price);
                statement.setInt(2, productId);
                statement.executeUpdate();
            }

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

    public void changeImage(int productId, String image) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            if(getProduct(productId) != null) {
                connection = database.getConnection();
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.image + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                statement = connection.prepareStatement(query);
                statement.setString(1, image);
                statement.setInt(2, productId);
                statement.executeUpdate();
            }
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
