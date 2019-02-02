package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Fleur van Eijk
 */
public class Cart {

    @NotEmpty
    @JsonView(View.Public.class)
    private String userEmail;

    @NotEmpty
    @JsonView(View.Public.class)
    private int productId;

    @NotEmpty
    @JsonView(View.Public.class)
    private int amount;

    public Cart(String userEmail, int productId, int amount) {
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
