package nl.hsleiden.model;

public class DatabaseInfo {

    public final static String userTableName = "user_account";
    public class userColumnNames{
        public static final String email = "email";
        public static final String name = "name";
        public static final String password = "password";
        public static final String role = "role";
    }

    public static final String productTableName = "product";
    public class ProductColumnNames{
        public static final String id = "id";
        public static final String title = "title";
        public static final String description  = "description";
        public static final String image  = "image";
        public static final String price  = "price";
    }

    public static final String basketTableName = "basket";
    public class basketColumnNames{
        public static final String id = "id";
        public static final String userEmail = "user_email";
    }

    public static final String orderedProductTableName = "ordered_product";
    public class orderedProductColumnNames{
        public static final String basketId = "basket_id";
        public static final String productId = "product_id";
        public static final String price = "price";
    }
}
