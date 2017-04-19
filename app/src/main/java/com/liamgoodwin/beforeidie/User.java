package com.liamgoodwin.beforeidie;

/**
 * Created by liamgoodwin on 2017-04-13.
 */

/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class User {
    private int id;
    private String username;
    private String password;
    private int privacy;

    /**
     * @param id is used to hold the id
     * @param username is a string for the username
     * @param password is a string for the password
     * @param privacy is a int to see if privacy is checkout or not
     */
    public User(int id, String username, String password, int privacy){
        this.id = id;
        this.username = username;
        this.password = password;
        this.privacy = privacy;
    }

    /**
     * @param username is a string for the username
     * @param password is a string for the password
     * @param privacy is a int to see if privacy is checkout or not
     */

    public User(String username, String password, int privacy){
        this.username = username;
        this.password = password;
        this.privacy = privacy;
    }


    /**
     * Used to create a new instance of the class
     */
    public User(){

    }

    /**
     * Getters and Setters
     */
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
