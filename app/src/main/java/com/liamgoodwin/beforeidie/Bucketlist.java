package com.liamgoodwin.beforeidie;

import android.widget.DatePicker;

import java.math.BigInteger;

/**
 * Created by JarrodMaeckeler on 2017-03-26.
 */

public class Bucketlist {

    private int id;
    private String name;
    private String description;
    private long time;
    private String bucketList;
    private int completed;

    public Bucketlist(String name, String description, long time, int completed){
        this.name = name;
        this.description = description;
        this.time = time;
        this.completed = completed;
    }

    public Bucketlist(int id, String name, String description, long time, int completed){
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.completed = completed;

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

    public long getTime() { return time; }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBucketlist() {
        return bucketList;
    }

    public void setBucketlist(String time) {
        this.bucketList = bucketList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompleted() {return completed;}

    public void setCompleted(int completed) {this.completed = completed;}

}
