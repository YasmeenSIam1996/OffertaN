package user.offerta.com.Module;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int product_id;
    private List<String> image;
    private String name;
    private int category_id;
    private double price;
    private String category;
    private int is_favorite;
    private int favorite_count;
    private int quantity;

    public int getProduct_id() {
        return product_id;
    }

    public List<String> getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public int getQuantity() {
        return quantity;
    }
}
