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
public class ProductService {

    private final ProductDAO dao;

    @Inject
    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public Collection<Product> getAll() {
        return dao.getAll();
    }

    public Product getProduct(int productId) {
        return dao.getProduct(productId);
    }

    public void add(Product product) {
        dao.add(product);
    }

    public void update(int id, Product product) {
        dao.update(id, product);
    }

    public void delete(int productId) {
        dao.delete(productId);
    }
}
