package main.simpledog.mobile.app.rest.entities;


import org.json.JSONArray;
import org.json.JSONObject;

public class Item {


   public String City;

    public String District;

    public String item_title;

    public String item_description;


    public String Date;


    public Item(){}


    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getItem_title() {
        return item_title;
    }
}
