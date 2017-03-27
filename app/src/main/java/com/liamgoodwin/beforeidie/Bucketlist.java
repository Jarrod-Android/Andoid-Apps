package com.liamgoodwin.beforeidie;

import java.math.BigInteger;

/**
 * Created by JarrodMaeckeler on 2017-03-26.
 */

public class Bucketlist {

    private String name;
    private String description;
    private int time;

    public Bucketlist(String name, String description, int time){
        this.name = name;
        this.description = description;
        this.time = time;
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
}
