package user.offerta.com.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryContent implements Serializable {
    private List<MainCategory> categories;
    private List<Product> products;
    private int last_page;
    private int product_count;
    @SerializedName("final")
    private boolean finalCont;

    public List<MainCategory> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getProduct_count() {
        return product_count;
    }

    public boolean isFinalCont() {
        return finalCont;
    }
}
