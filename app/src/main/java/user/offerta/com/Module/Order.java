package user.offerta.com.Module;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String comment;
    private double total_price;
    private String date;
    private String time;
    private int status;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
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

    public int getStatus() {
        return status;
    }
}
