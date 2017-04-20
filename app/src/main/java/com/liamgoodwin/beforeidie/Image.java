package com.liamgoodwin.beforeidie;

/**
 * @author Jarrod and Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class Image {
    private int id;
    private String resource;

    /**
     * @author Jarrod and Liam
     * @version 1.0
     * @date April 19th, 2017
     *
     * Image constructor
     *
     */
    public Image(){

    }

    /**
     * @author Jarrod and Liam
     * @version 1.0
     * @date April 19th, 2017
     *
     * @param resource Passes this resource into the Constructor and
     * sets it to this instance of the Image class
     */
    public Image(String resource){
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
