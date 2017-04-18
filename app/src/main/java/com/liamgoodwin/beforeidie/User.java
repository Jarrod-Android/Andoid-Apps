package com.liamgoodwin.beforeidie;

/**
 * Created by liamgoodwin on 2017-04-13.
 */

public class User {
    private int id;
    private String username;
    private String password;
    private int privacy;

    public User(int id, String username, String password, int privacy){
        this.id = id;
        this.username = username;
        this.password = password;
        this.privacy = privacy;
    }

    public User(String username, String password, int privacy){
        this.username = username;
        this.password = password;
        this.privacy = privacy;
    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }
}
