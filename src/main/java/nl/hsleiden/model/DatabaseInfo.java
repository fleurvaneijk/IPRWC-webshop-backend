package nl.hsleiden.model;

/**
 * This class defines all the names in the database
 * @author Fleur van Eijk
 */
public class DatabaseInfo {

    // USERS
    public final static String userTableName = "user_account";
    public class userColumnNames{
        public static final String email = "email";
        public static final String name = "name";
        public static final String password = "password";
        public static final String role = "role";
    }

    // PRODUCTS
    public static final String productTableName = "product";
    public class productColumnNames{
        public static final String id = "id";
        public static final String title = "title";
        public static final String description  = "description";
        public static final String image  = "image";
        public static final String price  = "price";
    }

    // PRODUCT IMAGES
    public static final String imageTableName = "product_image";
    public class imageColumnNames{
        public static final String productId = "product_id";
        public static final String image  = "image";
    }

    // CART
    public static final String cartTableName = "cart";
    public class cartColumnNames{
        public static final String userEmail = "user_email";
        public static final String productId = "product_id";
        public static final String amount = "amount";
    }
}
