package main.simpledog.mobile.app.rest.parsers;


import android.util.Log;
import main.simpledog.mobile.app.rest.entities.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ItemResponseParser extends JsonBuilderBase<Item> {

    public ItemResponseParser(){
        super();
    }

    @Override
    public List<Item> Parse(JSONArray response)throws JSONException{
        responseResult = response;
        for(int i = 0; i <  responseResult.length(); i++){
            JSONObject res = (JSONObject)response.get(i);
            preparedList.add(i,BuildItem(res));
        }
        return  preparedList;
    }

    public  Item BuildItem(JSONObject res) throws  JSONException{
            Item item = new Item();
            item.item_title = getFirst(res,"item_title","item_descriptions");
            item.id = res.getString("id");
            return  item;
    }
}
