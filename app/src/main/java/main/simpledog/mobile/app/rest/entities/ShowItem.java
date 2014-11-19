package main.simpledog.mobile.app.rest.entities;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.util.Objects;

public class ShowItem extends Item {

    public  int id;
    public  String  title;
    public  String  description;
    public  String  category;
    public  String  published;
    public  String  modified;
    public  String  location;
    public  String  rate;


    public ShowItem(JSONObject obj){

    }

    ShowItem toObj(JSONObject obj){
        Gson g = new GsonBuilder().create();

      return   g.fromJson(obj.toString(),this.getClass());
    }
}
