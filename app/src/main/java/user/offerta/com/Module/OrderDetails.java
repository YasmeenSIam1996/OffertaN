package user.offerta.com.Module;

import java.io.Serializable;
import java.util.List;

public class OrderDetails  implements Serializable {
    private int id;
    private String comment;
    private double total_price;
    private String date;
    private String time;
    private int status;
    private int user_id;
    private int type;
    private String driver_name;
    private String driver_phone;
    private int payment;
    private double lat;
    private double lng;
    private String name;
    private String phone;
    private int department_id;
    private double price;
    private float rating;
    private String destination;
    private String photo0;
    private String photo1;
    private String photo2;
    private String image;
    private List<ProductItem> product;

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getType() {
        return type;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public int getPayment() {
        return payment;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public double getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public String getDestination() {
        return destination;
    }

    public String getPhoto0() {
        return photo0;
    }

    public String getPhoto1() {
        return photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public String getImage() {
        return image;
    }

    public List<ProductItem> getProduct() {
        return product;
    }
}
