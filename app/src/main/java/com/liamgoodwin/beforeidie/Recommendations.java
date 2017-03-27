package com.liamgoodwin.beforeidie;

/**
 * Created by liamgoodwin on 2017-03-25.
 */

public class Recommendations {

    private String name;
    private String description;
    private String recommendation;

    public Recommendations(String name, String description, String recommendation){
        this.name = name;
        this.description = description;
        this.recommendation = recommendation;
    }

    public String toString(){
        return getName();
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

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String location) {
        this.recommendation = location;
    }
}
