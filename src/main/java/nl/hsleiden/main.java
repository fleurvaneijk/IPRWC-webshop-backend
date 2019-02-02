package nl.hsleiden;

import nl.hsleiden.model.Cart;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.CartDAO;
import nl.hsleiden.persistence.ProductDAO;
import nl.hsleiden.persistence.UserDAO;
import nl.hsleiden.service.CartService;

public class main {
    public static void main(String[] args) {
        UserDAO userdao = new UserDAO();
        userdao.getAll();
        userdao.add(new User("test2@test.nl", "test", "wachtwoord", "GUEST"));

//        ProductDAO productDAO = new ProductDAO();
//
//        productDAO.getAll();
//        productDAO.add(new Product("TestProduct", "Het eerste product in de shop!", "image", 1.99));
//        productDAO.delete(8);
//        productDAO.changeDescription(8, "Toch niet het eerste product, maar wel de goedkoopste!");
//        System.out.println("\n");
//        productDAO.getAll();
//
//        CartDAO cart = new CartDAO();
//        cart.getCart(1);
//
//        CartService bs = new CartService(cart);
//        bs.addToCart(new Cart("fleur.vaneijk99@gmail.com", 2, 3));



    }
}
