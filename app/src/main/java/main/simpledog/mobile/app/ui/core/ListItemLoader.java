package main.simpledog.mobile.app.ui.core;


import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.rest.entities.ShowItem;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListItemLoader {


    protected String  show_item_url = "/show_item";

    public  static  Integer loadItemTimeout = 5000;

    public void showItem(String item_id,final LoadItemsInterface loadItemsInterface){
        ItemResolverClient.get(show_item_url, new RequestParams("id",item_id), loadItemTimeout, new JsonHttpResponseHandler() {
            public void onStart() {
                loadItemsInterface.onStart();
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                List<Item> item = new ArrayList<Item>(1);

                item.add(new GsonBuilder().create().fromJson(response.toString(), ShowItem.class));

                loadItemsInterface.onSuccess(statusCode, item);
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                loadItemsInterface.onFailure(statusCode);
            }
            public final void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                loadItemsInterface.onFailure(statusCode);
            }

            public void onFinish() {
                loadItemsInterface.onFinish();
            }
        });
    }


    public interface LoadItemsInterface{
        public  void onStart();
        public void onSuccess(int statusCode,List<Item> items );

        public  void onFailure(int statusCode);
        public void onFinish();
    }




}


