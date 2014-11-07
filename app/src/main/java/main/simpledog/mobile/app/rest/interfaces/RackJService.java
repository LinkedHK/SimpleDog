package main.simpledog.mobile.app.rest.interfaces;


import main.simpledog.mobile.app.rest.entities.Item;
import retrofit.http.GET;

import java.util.List;

public interface RackJService {

    @GET("/items")
    List<Item> listItems();



}
