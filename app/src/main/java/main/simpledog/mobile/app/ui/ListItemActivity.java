package main.simpledog.mobile.app.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.rest.parsers.ItemResponseParser;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.controllers.LoadIndicator;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class ListItemActivity extends ListActivity {

    LoadIndicator loadIndicator;

    ItemResponseParser itemResponseParser;

    List<Item> items;

    ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        loadIndicator = new LoadIndicator(this,R.id.itemLoadingProgressBar);
        itemResponseParser = new ItemResponseParser();
        LoadItems();
    }
    protected void LoadItems() {
        ItemResolverClient.get("/items",null,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                loadIndicator.hide();
                    try{
                        items = itemResponseParser.Parse(response);
                    }catch (JSONException e){
                      Log.e("Json_Exception",e.getMessage());
                    }
                adapter = new ListItemAdapter(ListItemActivity.this,items);
                setListAdapter(adapter);
            }
        });
    }

}
