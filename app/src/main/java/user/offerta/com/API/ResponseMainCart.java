package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import user.offerta.com.Module.MainCart;

public class ResponseMainCart {
    private boolean status;
    private String message;
    @SerializedName("data")
    private MainCart mainCart;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public MainCart getMainCart() {
        return mainCart;
    }
}
