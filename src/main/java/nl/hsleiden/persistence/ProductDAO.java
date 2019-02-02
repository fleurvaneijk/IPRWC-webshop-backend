package nl.hsleiden.persistence;

import nl.hsleiden.model.DatabaseInfo;
import nl.hsleiden.model.Image;
import nl.hsleiden.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fleur van Eijk
 */
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
                statement1.close();
                statement2.close();
                resultSet1.close();
                resultSet2.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
//
//    public Product getProduct(int productId) {
//        Product product = null;
//        try{
//            String query = "SELECT * FROM " + DatabaseInfo.productTableName + " WHERE  " + DatabaseInfo.productColumnNames.id + " = ?";
//            PreparedStatement statement = database.getConnection().prepareStatement(query);
//            statement.setInt(1, productId);
//            resultSet = statement.executeQuery();
//
//            while(resultSet.next()){
//                product = new Product(resultSet.getInt(DatabaseInfo.productColumnNames.id), resultSet.getString(DatabaseInfo.productColumnNames.title),
//                        resultSet.getString(DatabaseInfo.productColumnNames.description), resultSet.getString(DatabaseInfo.productColumnNames.image),
//                        resultSet.getDouble(DatabaseInfo.productColumnNames.price));
//            }
//            return product;
//        }catch(SQLException sqle) {
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            try{
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void add(Product product) {
//        try {
//            String query = "INSERT INTO " + DatabaseInfo.productTableName + "(" + DatabaseInfo.productColumnNames.title + "," + DatabaseInfo.productColumnNames.description
//                            + "," + DatabaseInfo.productColumnNames.image + "," + DatabaseInfo.productColumnNames.price +") VALUES(?, ?, ?, ?)";
//            PreparedStatement statement = database.getConnection().prepareStatement(query);
//            statement.setString(1, product.getTitle());
//            statement.setString(2, product.getDescription());
//            statement.setString(3, product.getImagePath());
//            statement.setDouble(4, product.getPrice());
//            statement.executeUpdate();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void delete(int productId) {
//        try{
//            if(getProduct(productId) != null)
//            {
//                String query = "DELETE FROM " + DatabaseInfo.productTableName + " WHERE " + DatabaseInfo.productColumnNames.id + " =?;";
//                PreparedStatement statement = database.getConnection().prepareStatement(query);
//                statement.setInt(1, productId);
//                statement.executeUpdate();
//            }
//        }catch(SQLException sqle) {
//            sqle.printStackTrace();
//        }
//        finally {
//            try{
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changeTitle(int productId, String title) {
//        try {
//            if(getProduct(productId) != null)
//            {
//                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.title + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
//                PreparedStatement statement = database.getConnection().prepareStatement(query);
//                statement.setString(1, title);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changeDescription(int productId, String description) {
//        try {
//            if(getProduct(productId) != null)
//            {
//                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.description + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
//                PreparedStatement statement = database.getConnection().prepareStatement(query);
//                statement.setString(1, description);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changePrice(int productId, Double price) {
//        try {
//            if(getProduct(productId) != null)
//            {
//                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.price + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
//                PreparedStatement statement = database.getConnection().prepareStatement(query);
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
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void changeImage(int productId, String image) {
//        try {
//            if(getProduct(productId) != null)
//            {
//                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.image + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
//                PreparedStatement statement = database.getConnection().prepareStatement(query);
//                statement.setString(1, image);
//                statement.setInt(2, productId);
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                resultSet.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }
}
