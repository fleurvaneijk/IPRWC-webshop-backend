package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Product {
    @NotEmpty
    @JsonView(View.Public.class)
    private String title;

    @NotEmpty
    @JsonView(View.Public.class)
    private String description;

    @NotEmpty
    @JsonView(View.Public.class)
    private String imagePath;

    @NotEmpty
    @JsonView(View.Public.class)
    private double price;


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
