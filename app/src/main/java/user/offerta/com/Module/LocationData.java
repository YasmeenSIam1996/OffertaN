package user.offerta.com.Module;

import java.io.Serializable;

public class LocationData implements Serializable {

    private int id;
    private int user_id;
    private String name;
    private double lat;
    private double lng;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
