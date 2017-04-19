package com.liamgoodwin.beforeidie;

/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */
public class Recommendation {

    private int id;
    private String name;
    private String description;
    private int image;


    /**
     *
     * @param name used to pass the string of name
     * @param description used to pass the string of description
     * @param image used to pass the int of image
     */
    public Recommendation(String name, String description, int image){
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     *
     * @param id used to pass the int of id
     * @param name used to pass the string of name
     * @param description used to pass the string of description
     * @param image used to pass the int of image
     */
    public Recommendation(int id, String name, String description, int image){
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     * Empty constructor
     */
    public Recommendation(){

    }


    // getters and setters
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

    public int getImage() { return image; }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
