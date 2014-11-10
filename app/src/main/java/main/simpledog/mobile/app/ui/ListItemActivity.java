package main.simpledog.mobile.app.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.loopj.android.http.JsonHttpResponseHandler;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.rest.parsers.ItemResponseParser;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class ListItemActivity extends ListActivity  {

    protected ProgressBar loaderView;

    ItemResponseParser itemResponseParser;

    List<Item> items;

    ListItemAdapter adapter;

    String items_url = "/items";

    Integer loadItemTimeout = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        loaderView = (ProgressBar) findViewById(R.id.itemLoadingProgressBar);
        itemResponseParser = new ItemResponseParser();
        getListView().setOnScrollListener(scrollListener);
        LoadItems();
    }

        AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("firstVisibleItem", String.valueOf( firstVisibleItem) );

                Log.d("visibleItemCount", String.valueOf( visibleItemCount) );


            }
        };


    protected void LoadItems() {
        ItemResolverClient.get(items_url,null,loadItemTimeout,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                loaderView.setVisibility(View.INVISIBLE);
                try{
                    items = itemResponseParser.Parse(response);
                }catch (JSONException e){
                    Log.e("Json_Exception",e.getMessage());
                }
                adapter = new ListItemAdapter(ListItemActivity.this,items);
                setListAdapter(adapter);
            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                loaderView.setVisibility(View.INVISIBLE);
                ItemDialogs.itemLoadFailure(ListItemActivity.this);
            }
        });
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Item_id", items.get(position).id );

    }
}
