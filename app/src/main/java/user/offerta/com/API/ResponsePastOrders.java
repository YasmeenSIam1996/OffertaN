package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import user.offerta.com.Module.Order;

public class ResponsePastOrders implements Serializable {
    private boolean status;
    private String message;
    @SerializedName("data")
    private List<Order> orderList;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}
