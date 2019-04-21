package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.Advertisement;
import user.offerta.com.Module.Product;

public class ResponseAdvertisement {
    private boolean status;
    private String message;
    @SerializedName("data")
    private List<Advertisement> advertisements;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }
}
