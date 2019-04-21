package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import user.offerta.com.Module.LocationData;

public class ResponseLocation {
    private boolean status;
    private String message;
    @SerializedName("data")
    private List<LocationData> locationData;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<LocationData> getLocationData() {
        return locationData;
    }
}
