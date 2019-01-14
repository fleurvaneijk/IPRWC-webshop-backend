package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Fleur van Eijk
 */
public class Product {
    @JsonView(View.Public.class)
    private int product_id;

    @NotEmpty
    @JsonView(View.Public.class)
    private String title;

    @JsonView(View.Public.class)
    private String description;

    @JsonView(View.Public.class)
    private String imagePath;

    @NotEmpty
    @JsonView(View.Public.class)
    private double price;

    public Product(int id, String title, String description, String imagePath, double price) {
        this.product_id = id;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }

    public Product(String title, String description, String imagePath, double price) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
