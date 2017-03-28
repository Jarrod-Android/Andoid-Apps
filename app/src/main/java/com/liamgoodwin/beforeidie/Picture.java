package com.liamgoodwin.beforeidie;

/**
 * Created by JarrodMaeckeler on 2017-03-27.
 */

public class Picture {

    private int id;
    private String resource;

    public Picture(int id, String resource){
        this.id = id;
        this.resource = resource;
    }
    public Picture(){

    }
    public Picture(String resource){
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}