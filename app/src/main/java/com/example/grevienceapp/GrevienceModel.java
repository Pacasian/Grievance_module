package com.example.grevienceapp;

/**
 * sample model structure of list view for grevience
 */

public class GrevienceModel
{

    public String id; //name
    public String name; //timedate
    public String number;
    public GrevienceModel(String name, String img,String number)
    {
        this.id = img;
        this.name = name;
        this.number=number;

    }

    public String getImg() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }

}
