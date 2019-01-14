package nl.hsleiden.persistence;

import nl.hsleiden.model.DatabaseInfo;
import nl.hsleiden.model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final DatabaseConnection database = new DatabaseConnection();
    ArrayList<Product> products = new ArrayList<>();
    private ResultSet resultSet = null;

    public List<Product> getAll() {
        try {
            String query = "SELECT * FROM " + DatabaseInfo.productTableName;
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Product product = new Product (resultSet.getInt(DatabaseInfo.productColumnNames.id), resultSet.getString(DatabaseInfo.productColumnNames.title),
                        resultSet.getString(DatabaseInfo.productColumnNames.description) ,resultSet.getString(DatabaseInfo.productColumnNames.image),
                        resultSet.getDouble(DatabaseInfo.productColumnNames.price));

                System.out.println(product.getTitle() + "\t" +  product.getDescription() + "\t" + product.getPrice());
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProduct(int productId) {
        Product product = null;
        try{
            String query = "SELECT * FROM " + DatabaseInfo.productTableName + " WHERE  " + DatabaseInfo.productColumnNames.id + " = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setInt(1, productId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                product = new Product(resultSet.getInt(DatabaseInfo.productColumnNames.id), resultSet.getString(DatabaseInfo.productColumnNames.title),
                        resultSet.getString(DatabaseInfo.productColumnNames.description), resultSet.getString(DatabaseInfo.productColumnNames.image),
                        resultSet.getDouble(DatabaseInfo.productColumnNames.price));
            }
            return product;
        }catch(SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void add(Product product) {
        try {
            String query = "INSERT INTO " + DatabaseInfo.productTableName + "(" + DatabaseInfo.productColumnNames.title + "," + DatabaseInfo.productColumnNames.description
                            + "," + DatabaseInfo.productColumnNames.image + "," + DatabaseInfo.productColumnNames.price +") VALUES(?, ?, ?, ?)";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getImagePath());
            statement.setDouble(4, product.getPrice());
            statement.execute();
        }catch(Exception e){
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

    public void delete(int productId) {
        try{
            if(getProduct(productId) != null)
            {
                String query = "DELETE FROM " + DatabaseInfo.productTableName + " WHERE " + DatabaseInfo.productColumnNames.id + " =?;";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setInt(1, productId);
                statement.execute();
            }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void changeTitle(int productId, String title) {
        try {
            if(getProduct(productId) != null)
            {
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.title + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, title);
                statement.setInt(2, productId);
                statement.execute();
            }

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

    public void changeDescription(int productId, String description) {
        try {
            if(getProduct(productId) != null)
            {
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.description + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, description);
                statement.setInt(2, productId);
                statement.execute();
            }

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

    public void changePrice(int productId, Double price) {
        try {
            if(getProduct(productId) != null)
            {
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.price + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setDouble(1, price);
                statement.setInt(2, productId);
                statement.execute();
            }

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

    public void changeImage(int productId, String image) {
        try {
            if(getProduct(productId) != null)
            {
                String query = "UPDATE " + DatabaseInfo.productTableName + " SET " + DatabaseInfo.productColumnNames.image + " = ? WHERE " + DatabaseInfo.productColumnNames.id + " = ?;";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, image);
                statement.setInt(2, productId);
                statement.execute();
            }

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
