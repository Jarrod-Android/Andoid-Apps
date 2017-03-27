package com.liamgoodwin.beforeidie;

import java.math.BigInteger;

/**
 * Created by JarrodMaeckeler on 2017-03-26.
 */

public class Bucketlist {

    private String name;
    private String description;
    private BigInteger time;

    public Bucketlist(String name, String description, BigInteger time){
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

    public BigInteger getTime() {
        return time;
    }

    public void setTime(BigInteger time) {
        this.time = time;
    }
}
