package Entity;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private String name;
    private String email;
    private List<String> mobileList;

    public User(long id, String name, String email, List<String> mobileList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileList = mobileList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getMobileList() {
        return mobileList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileList=" + mobileList +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileList(List<String> mobileList) {
        this.mobileList = mobileList;
    }
}
