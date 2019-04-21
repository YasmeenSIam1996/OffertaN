package user.offerta.com.Module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MainCategory implements Serializable {
    @SerializedName("id")
    private int CategoryId;
    private String image;
    private String name;

    public int getId() {
        return CategoryId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
