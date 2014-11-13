package main.simpledog.mobile.app.ui;


import android.util.Log;
import android.view.View;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.rest.parsers.ItemResponseParser;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ListItemLoader {

    protected  String items_url = "/items";

    protected  Integer loadItemTimeout = 5000;

    protected  List<Item> items;

    protected  ItemResponseParser itemResponseParser = new ItemResponseParser();

    protected  ListItemActivity itemActivity;

    protected  void LoadItems( final LoadItemsInterface loadItemsInterface){
        LoadItems(new RequestParams("page",0),loadItemsInterface );
    }

    protected void LoadItems(RequestParams params, final LoadItemsInterface loadItemsInterface) {
        ItemResolverClient.get(items_url, params, loadItemTimeout, new JsonHttpResponseHandler() {
            public void onStart() {
                loadItemsInterface.onStart();
            }
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    items = itemResponseParser.Parse(response);
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

    public interface LoadItemsInterface{
        public  void onStart();
        public void onSuccess(int statusCode,List<Item> items);
        public  void onFailure(int statusCode);
        public void onFinish();
    }
    public abstract class ShortItemLoader implements LoadItemsInterface{
        public  void onStart(){

        }
        public abstract void onSuccess(int statusCode,List<Item> items);

        public  void onFailure(int statusCode){

        }
        public void onFinish(){

        }

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

    public List<Item> getItems() {
        return items;
    }
}


