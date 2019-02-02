package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Model for the products
 * @author Fleur van Eijk
 */
public class Product {
    @JsonView(View.Public.class)
    private int id;

    @NotEmpty
    @JsonView(View.Public.class)
    private String title;

    @JsonView(View.Public.class)
    private String description;

    @JsonView(View.Public.class)
    private Image[] images;

    @NotEmpty
    @JsonView(View.Public.class)
    private double price;

    public Product(int id, String title, String description, Image[] images, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
        this.price = price;
    }

    public Product(String title, String description, Image[] images, double price) {
        this.title = title;
        this.description = description;
        this.images = images;
        this.price = price;
    }

    public int getId() {
        return id;
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

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
