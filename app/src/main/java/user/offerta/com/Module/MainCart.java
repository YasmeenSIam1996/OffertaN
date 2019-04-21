package user.offerta.com.Module;


import java.io.Serializable;
import java.util.List;

public class MainCart implements Serializable {
    private List<Product> products;
    private double tax;
    private double shipping;
    private double total_price;
    private double grant_totally;
    private int count;

    public List<Product> getProducts() {
        return products;
    }

    public double getTax() {
        return tax;
    }

    public double getShipping() {
        return shipping;
    }

    public double getTotal_price() {
        return total_price;
    }

    public double getGrant_totally() {
        return grant_totally;
    }

    public int getCount() {
        return count;
    }
}
