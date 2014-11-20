package main.simpledog.mobile.app.ui;


import android.util.Log;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.rest.entities.ShowItem;
import main.simpledog.mobile.app.rest.parsers.ItemResponseParser;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListItemLoader {

    protected  String items_url = "/items";

    protected String    show_item_url = "/show_item";

    protected  Integer loadItemTimeout = 5000;

    protected ListItemFragment itemActivity;

    public void loadItems(int page, String category, final LoadItemsInterface loadItemsInterface) {
        RequestParams params = prepareParams(page,category);
        ItemResolverClient.get(items_url, params, loadItemTimeout, new JsonHttpResponseHandler() {
            public void onStart() {
                loadItemsInterface.onStart();
            }
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Item> items = new ArrayList<Item>();
                try {
                    items  = new ItemResponseParser().Parse(response);
                } catch (JSONException e) {
                    Log.e("Json_Exception", e.getMessage());
                }
                loadItemsInterface.onSuccess(statusCode,items);

            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                loadItemsInterface.onFailure(statusCode);
            }
            public void onFinish() {
                loadItemsInterface.onFinish();
            }

        });
    }
    public void showItem(int item_id,final LoadItemsInterface loadItemsInterface){
        ItemResolverClient.get(show_item_url, new RequestParams("id",item_id), loadItemTimeout, new JsonHttpResponseHandler() {
            public void onStart() {
                loadItemsInterface.onStart();
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                List<Item> item = new ArrayList<Item>(1);

                item.add(new GsonBuilder().create().fromJson(response.toString(), ShowItem.class));

                loadItemsInterface.onSuccess(statusCode, item);
            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                loadItemsInterface.onFailure(statusCode);
            }
            public void onFinish() {
                loadItemsInterface.onFinish();
            }

        });


    }

    RequestParams prepareParams(int page, String category){

        RequestParams params = new RequestParams();
        params.add("page",String.valueOf(page));
        if (category == null){
            params.add("category","information-technology");
        }else {
            params.add("category",category);
        }
        return  params;
    };


    public interface LoadItemsInterface{
        public  void onStart();
        public void onSuccess(int statusCode,List<Item> items );

        public  void onFailure(int statusCode);
        public void onFinish();
    }

    public Integer getLoadItemTimeout() {
        return loadItemTimeout;
    }

    public void setLoadItemTimeout(Integer loadItemTimeout) {
        this.loadItemTimeout = loadItemTimeout;
    }

    public String getItems_url() {
        return items_url;
    }

    public void setItems_url(String items_url) {
        this.items_url = items_url;
    }


}


