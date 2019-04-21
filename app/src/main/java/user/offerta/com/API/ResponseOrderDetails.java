package user.offerta.com.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import user.offerta.com.Module.Order;
import user.offerta.com.Module.OrderItem;

public class ResponseOrderDetails implements Serializable {
    private boolean status;
    private String message;
    @SerializedName("data")
    private OrderItem orderItem;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }
}
