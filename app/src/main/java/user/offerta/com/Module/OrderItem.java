package user.offerta.com.Module;


import java.io.Serializable;

public class OrderItem implements Serializable {
    private double tax;
    private double shipping;
    private OrderDetails order;

    public double getTax() {
        return tax;
    }

    public double getShipping() {
        return shipping;
    }

    public OrderDetails getOrder() {
        return order;
    }
}
