package com.liamgoodwin.beforeidie;

import java.math.BigInteger;

/**
 * Created by JarrodMaeckeler on 2017-03-26.
 */

public class Bucketlist {

    private int id;
    private String name;
    private String description;
    private int time;

    public Bucketlist(String name, String description, int time){
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public Bucketlist(int id, String name, String description, int time){
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public Bucketlist(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
