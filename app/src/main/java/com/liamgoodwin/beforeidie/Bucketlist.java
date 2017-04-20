package com.liamgoodwin.beforeidie;

/**
 * @author Jarrod and Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class Bucketlist {

    private int id;
    private String name;
    private String description;
    private long time;
    private String bucketList;
    private int completed;

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * Bucketlist constructor for setting the name, description, time, and completed field
     *
     * @param  name  holds the name in a string
     * @param  description holds the description in a string
     * @param  time holds the time in a long
     * @param completed holds the completed int of true or false
     */
    public Bucketlist(String name, String description, long time, int completed){
        this.name = name;
        this.description = description;
        this.time = time;
        this.completed = completed;
    }

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * Bucketlist constructor for setting the name, description, time, and completed field
     *
     * @param  id  holds the id in an int
     * @param  name  holds the name in a string
     * @param  description holds the description in a string
     * @param  time holds the time in a long
     * @param completed holds the completed int of true or false
     */
    public Bucketlist(int id, String name, String description, long time, int completed){
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.completed = completed;
    }

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * Bucketlist base constructor
     */
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
