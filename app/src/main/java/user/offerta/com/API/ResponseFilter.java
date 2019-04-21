package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import user.offerta.com.Module.FilterItem;

public class ResponseFilter {
    private boolean status;
    private String message;
    @SerializedName("data")
    private FilterItem filterItem;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public FilterItem getMainCart() {
        return filterItem;
    }
}
