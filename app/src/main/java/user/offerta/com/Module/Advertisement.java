package user.offerta.com.Module;

import java.io.Serializable;

public class Advertisement implements Serializable {

    private int id;
    private String name;
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
