package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.Product;
import user.offerta.com.Module.User;

public class ResponseSign {
    private boolean status;
    private String message;
    @SerializedName("data")
    private User user;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
