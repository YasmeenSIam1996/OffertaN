package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.Cart;
import user.offerta.com.Module.Product;

public class ResponseAddToCart {
    private boolean status;
    private String message;
    @SerializedName("data")
    private Cart carts;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Cart getCarts() {
        return carts;
    }
}
