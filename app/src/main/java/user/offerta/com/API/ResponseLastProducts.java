package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.MainCategory;
import user.offerta.com.Module.Product;

public class ResponseLastProducts {
    private boolean status;
    private String message;
    @SerializedName("data")
    private List<Product> lastProducts;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Product> getLastProducts() {
        return lastProducts;
    }
}
