package nl.hsleiden.service;

import nl.hsleiden.model.Product;
import nl.hsleiden.persistence.ProductDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

/**
 * @author Fleur van Eijk
 */
@Singleton
public class ProductService extends BaseService{

    private final ProductDAO dao;

    @Inject
    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public Collection<Product> getAll() {
        return dao.getAll();
    }

    public Product getProduct(int productId) {
        return (Product) requireResult(dao.getProduct(productId));
    }

    public void add(Product product) {
        dao.add(product);
    }
//
//    public void delete(int productId) {
//        dao.delete(productId);
//    }
//
//    public void changeTitle(int productId, String title) {
//        dao.changeTitle(productId, title);
//    }
//
//    public void changeDescription(int productId, String description) {
//        dao.changeDescription(productId, description);
//    }
//
//    public void changePrice(int productId, Double price) {
//        dao.changePrice(productId, price);
//    }
//
//    public void changeImage(int productId, String image) {
//        dao.changeImage(productId, image);
//    }
}
