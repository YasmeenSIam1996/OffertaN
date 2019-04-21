package user.offerta.com.Module;

import java.io.Serializable;

public class DataProduct implements Serializable {
    private double tax;
    private double shipping;
    private ProductDetails product;

    public double getTax() {
        return tax;
    }

    public double getShipping() {
        return shipping;
    }

    public ProductDetails getProduct() {
        return product;
    }
}
