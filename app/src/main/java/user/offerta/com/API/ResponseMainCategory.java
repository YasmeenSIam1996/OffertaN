package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.MainCategory;

public class ResponseMainCategory {
    private boolean status;
    private String message;
    @SerializedName("data")
    private List<MainCategory> mainCategories;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<MainCategory> getMainCategories() {
        return mainCategories;
    }
}
