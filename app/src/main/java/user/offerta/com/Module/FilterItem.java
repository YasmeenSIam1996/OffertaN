package user.offerta.com.Module;

import java.io.Serializable;
import java.util.List;

public class FilterItem implements Serializable {
    private List<SizeFilter> sizes;
    private List<ColorPicker> colors;

    public List<SizeFilter> getSizes() {
        return sizes;
    }

    public List<ColorPicker> getColors() {
        return colors;
    }
}
