package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;
import user.offerta.com.Module.DataProduct;

public class ResponseProductDetails {
    private boolean status;
    private String message;
    @SerializedName("data")
    private DataProduct dataProduct;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataProduct getDataProduct() {
        return dataProduct;
    }
}
