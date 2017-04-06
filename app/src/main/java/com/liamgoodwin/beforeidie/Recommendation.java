package com.liamgoodwin.beforeidie;

public class Recommendation {

    private int id;
    private String name;
    private String description;
    private int image;

    public Recommendation(String name, String description, int image){
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Recommendation(int id, String name, String description, int image){
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Recommendation(){

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
