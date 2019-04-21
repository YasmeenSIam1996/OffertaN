package user.offerta.com.Module;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ProductDetails extends Product implements Serializable {
    private String description;
    private float rate;
    private int count_rate;
    @SerializedName("color")
    private List<ColorPicker> colorPicker;
    @SerializedName("size")
    private List<ProductSize> productSizes;

    public String getDescription() {
        return description;
    }

    public float getRate() {
        return rate;
    }

    public int getCount_rate() {
        return count_rate;
    }

    public List<ColorPicker> getColorPicker() {
        return colorPicker;
    }

    public List<ProductSize> getProductSizes() {
        return productSizes;
    }
}
