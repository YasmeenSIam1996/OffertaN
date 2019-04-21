package user.offerta.com.API;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import user.offerta.com.Module.User;


public class ResponseUser implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private User user;



    public ResponseUser() {
    }

    public ResponseUser(boolean status, String message, User items) {
        this.status = status;
        this.message = message;
        this.user = items;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User items) {
        this.user = items;
    }
}
