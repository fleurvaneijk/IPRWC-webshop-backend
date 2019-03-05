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
 * DAO that gets data from, and writes to the product table
 * @author Fleur van Eijk
 */
@Singleton
public class ProductDAO {
    private ArrayList<Product> products;

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
            String query1 = "SELECT * FROM " + DatabaseInfo.productTable;
            statement1 = connection.prepareStatement(query1);
            resultSet1 = statement1.executeQuery();

            while (resultSet1.next()) {
                String query2 = "SELECT "+ DatabaseInfo.imageColumn.image + " FROM " + DatabaseInfo.imageTable + " WHERE product_id = " + resultSet1.getInt(DatabaseInfo.productColumn.id);
                statement2 = connection.prepareStatement(query2);
                resultSet2 = statement2.executeQuery();

                ArrayList<String> images = new ArrayList<String>();

                while(resultSet2.next()){
                    String image = resultSet2.getString(DatabaseInfo.imageColumn.image);
                    images.add(image);
                }

                Product product = new Product (resultSet1.getInt(DatabaseInfo.productColumn.id), resultSet1.getString(DatabaseInfo.productColumn.title),
                        resultSet1.getString(DatabaseInfo.productColumn.description), images, resultSet1.getDouble(DatabaseInfo.productColumn.price));

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
            String query = "SELECT * FROM " + DatabaseInfo.productTable + " WHERE  " + DatabaseInfo.productColumn.id + " = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setInt(1, productId);
            resultSet1 = statement1.executeQuery();
            resultSet1.next();

            String query2 = "SELECT "+ DatabaseInfo.imageColumn.image + " FROM " + DatabaseInfo.imageTable + " WHERE product_id = " + resultSet1.getInt(DatabaseInfo.productColumn.id);
            statement2 = connection.prepareStatement(query2);
            resultSet2 = statement2.executeQuery();

            ArrayList<String> images = new ArrayList<String>();

            while(resultSet2.next()){
                String image = resultSet2.getString(DatabaseInfo.imageColumn.image);
                images.add(image);
            }
            product = new Product(resultSet1.getInt(DatabaseInfo.productColumn.id), resultSet1.getString(DatabaseInfo.productColumn.title),
                    resultSet1.getString(DatabaseInfo.productColumn.description), images, resultSet1.getDouble(DatabaseInfo.productColumn.price));
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
        PreparedStatement statementCurrval = null;
        ResultSet resultset = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "INSERT INTO " + DatabaseInfo.productTable + " VALUES(DEFAULT, ?, ?, ?);";
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.execute();

            String queryCurrval = "SELECT currval(pg_get_serial_sequence('" + DatabaseInfo.productTable + "', '" + DatabaseInfo.productColumn.id + "'));";
            statementCurrval = connection.prepareStatement(queryCurrval);
            resultset = statementCurrval.executeQuery();
            resultset.next();
            int lastId = resultset.getInt("currval");

            insertImages(lastId, product);

        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private void insertImages(int id, Product product) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();

            for (String image : product.getImages()) {
                String query2 = "INSERT INTO " + DatabaseInfo.imageTable + " VALUES(?, ?)";
                statement = connection.prepareStatement(query2);
                statement.setInt(1, id);
                statement.setString(2, image);
                statement.execute();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(int id, Product newProduct) {
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "UPDATE " + DatabaseInfo.productTable + " SET " + DatabaseInfo.productColumn.title + " = ? , "
                    + DatabaseInfo.productColumn.description + " = ? , " + DatabaseInfo.productColumn.price + " = ? "
                    + " WHERE " + DatabaseInfo.productColumn.id + " = ?;";

            statement = connection.prepareStatement(query);
            statement.setString(1, newProduct.getTitle());
            statement.setString(2, newProduct.getDescription());
            statement.setDouble(3, newProduct.getPrice());
            statement.setInt(4, id);
            statement.executeUpdate();

            // Delete images and instert new images
            String query2 = "DELETE FROM " + DatabaseInfo.imageTable + " WHERE " + DatabaseInfo.imageColumn.productId + " = ?";
            statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, newProduct.getId());
            statement2.executeUpdate();
            insertImages(newProduct.getId(), newProduct);

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

    public void delete(int productId) {
        PreparedStatement statement = null;
        Connection connection = null;
        try{
            if(getProduct(productId) != null) {
                connection = database.getConnection();
                String query = "DELETE FROM " + DatabaseInfo.productTable + " WHERE " + DatabaseInfo.productColumn.id + " =?;";
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

//    public void changeTitle(int productId, String title) {
//        PreparedStatement statement = null;
//        Connection connection = null;
//        try {
//            if(getProduct(productId) != null) {
//                connection = database.getConnection();
//                String query = "UPDATE " + DatabaseInfo.productTable + " SET " + DatabaseInfo.productColumn.title + " = ? WHERE " + DatabaseInfo.productColumn.id + " = ?;";
//                statement = connection.prepareStatement(query);
//                statement.setString(1, title);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                Objects.requireNonNull(statement).close();
//                connection.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changeDescription(int productId, String description) {
//        PreparedStatement statement = null;
//        Connection connection = null;
//        try {
//            if(getProduct(productId) != null) {
//                connection = database.getConnection();
//                String query = "UPDATE " + DatabaseInfo.productTable + " SET " + DatabaseInfo.productColumn.description + " = ? WHERE " + DatabaseInfo.productColumn.id + " = ?;";
//                statement = connection.prepareStatement(query);
//                statement.setString(1, description);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                Objects.requireNonNull(statement).close();
//                connection.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changePrice(int productId, Double price) {
//        PreparedStatement statement = null;
//        Connection connection = null;
//        try {
//            if(getProduct(productId) != null) {
//                connection = database.getConnection();
//                String query = "UPDATE " + DatabaseInfo.productTable + " SET " + DatabaseInfo.productColumn.price + " = ? WHERE " + DatabaseInfo.productColumn.id + " = ?;";
//                statement = connection.prepareStatement(query);
//                statement.setDouble(1, price);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                Objects.requireNonNull(statement).close();
//                connection.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changeImage(int productId, String image) {
//        PreparedStatement statement = null;
//        Connection connection = null;
//        try {
//            if(getProduct(productId) != null) {
//                connection = database.getConnection();
//                String query = "UPDATE " + DatabaseInfo.productTable + " SET " + DatabaseInfo.productColumn.image + " = ? WHERE " + DatabaseInfo.productColumn.id + " = ?;";
//                statement = connection.prepareStatement(query);
//                statement.setString(1, image);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                Objects.requireNonNull(statement).close();
//                connection.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
}
