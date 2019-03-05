package nl.hsleiden.model;

/**
 * This class defines all the names in the database
 * @author Fleur van Eijk
 */
public class DatabaseInfo {

    // USERS
    public final static String userTable = "user_account";
    public class userColumn {
        public static final String email = "email";
        public static final String name = "name";
        public static final String password = "password";
        public static final String role = "role";
    }

    // PRODUCTS
    public static final String productTable = "product";
    public class productColumn {
        public static final String id = "id";
        public static final String title = "title";
        public static final String description  = "description";
        public static final String price  = "price";
    }

    // PRODUCT IMAGES
    public static final String imageTable = "product_image";
    public class imageColumn {
        public static final String productId = "product_id";
        public static final String image  = "image";
    }

    // CART
    public static final String cartTable = "cart";
    public class cartColumn {
        public static final String userEmail = "user_email";
        public static final String productId = "product_id";
        public static final String amount = "amount";
    }
}
