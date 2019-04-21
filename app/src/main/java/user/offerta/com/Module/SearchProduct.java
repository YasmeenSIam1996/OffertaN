package user.offerta.com.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchProduct implements Serializable {

    @SerializedName("products")
    private List<Product> productList;
    private int last_page;
    private int product_count;

    public List<Product> getProductList() {
        return productList;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getProduct_count() {
        return product_count;
    }
}
