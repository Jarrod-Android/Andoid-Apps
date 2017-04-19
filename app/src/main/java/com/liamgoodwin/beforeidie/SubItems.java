package com.liamgoodwin.beforeidie;

import java.util.List;

/**
 * Created by JarrodMaeckeler on 2017-04-12.
 */

public class SubItems {

    private int column_id;
    private int item_id;
    private String item_name;

    public SubItems(int column_id, int item_id, String item_name) {
        this.column_id = column_id;
        this.item_id = item_id;
        this.item_name = item_name;
    }

    public SubItems(int column_id, String item_name) {
        this.column_id = column_id;
        this.item_name = item_name;
    }

    public SubItems() {

    }


    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String toString() {
       return "id " + this.item_id +
        "name " + this.item_name +
        "column id " + this.column_id;
    }
}
