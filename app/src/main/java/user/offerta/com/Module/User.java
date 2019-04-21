package user.offerta.com.Module;

import java.io.Serializable;

import io.realm.RealmObject;

public class User extends RealmObject implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int isVerified;
    private String token;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
