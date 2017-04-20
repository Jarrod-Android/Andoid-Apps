package com.liamgoodwin.beforeidie;



public class Picture {

    private int id;
    private String resource;


    /**
     *
     * @param id used to send int id
     * @param resource used to send the String resource
     */
    public Picture(int id, String resource){
        this.id = id;
        this.resource = resource;
    }

    /**
     * Empty constructor
     */
    public Picture(){

    }

    //getters and setters

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