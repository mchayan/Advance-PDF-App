package com.editmypdffree.rdtl.adapter;


import java.util.ArrayList;

public class SettersAndGetters {

    //    vars


    //private String Thumbnail;
    private ArrayList Thumbnail;

    //    constructor
    public SettersAndGetters(ArrayList thumbnail){

        Thumbnail = thumbnail;
    }




//    public String getTitle() {
//        return Title;
//    }
//
//    public void setTitle(String title) {
//        Title = title;
//    }
//
//
//    public String getCategory() {
//        return Category;
//    }
//
//    public void setCategory(String category) {
//        Category = category;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
    public ArrayList getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(ArrayList thumbnail) {
        Thumbnail = thumbnail;
    }
}

