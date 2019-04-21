package user.offerta.com.Module;

import java.io.Serializable;

public class ColorPicker implements Serializable {

    private int color_id;
    private String name;
    private String hex;

    public int getColor_id() {
        return color_id;
    }

    public String getName() {
        return name;
    }

    public String getHex() {
        return hex;
    }
}
