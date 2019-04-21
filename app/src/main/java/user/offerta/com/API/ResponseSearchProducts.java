package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.Product;
import user.offerta.com.Module.SearchProduct;

public class ResponseSearchProducts {
    private boolean status;
    private String message;
    @SerializedName("data")
    private SearchProduct searchProduct;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public SearchProduct getSearchProduct() {
        return searchProduct;
    }
}
