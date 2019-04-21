package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import user.offerta.com.Module.CategoryContent;

public class ResponseCategoryContent {
    private boolean status;
    private String message;
    @SerializedName("data")
    private CategoryContent categoryContent;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CategoryContent getCategoryContent() {
        return categoryContent;
    }
}
