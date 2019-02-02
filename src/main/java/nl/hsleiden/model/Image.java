package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

public class Image {
    @NotEmpty
    @JsonView(View.Public.class)
    private int product_id;

    @JsonView(View.Public.class)
    private String imagePath;

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
