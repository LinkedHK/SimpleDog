package main.simpledog.mobile.app.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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

   private  ListItemAdapter adapter;

    private   ListItemLoader itemLoader = new ListItemLoader();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        loaderView = (ProgressBar) findViewById(R.id.itemLoadingProgressBar);
        itemLoader.LoadItems(loadItemsCallback);
      //  getListView().setOnScrollListener(scrollListener);
    }


    ListItemLoader.LoadItemsInterface loadItemsCallback  = new ListItemLoader.LoadItemsInterface() {
        @Override
        public void onStart() {
            loaderView.setVisibility(View.VISIBLE);
        }
        @Override
        public void onSuccess(int statusCode, List<Item> items) {
            if(adapter == null){
                adapter = new ListItemAdapter(ListItemActivity.this,items);
                setListAdapter(adapter);
            }else {
                adapter.addAll(items);
            }
        }
        @Override
        public void onFailure(int statusCode) {
            ItemDialogs.itemLoadFailure(ListItemActivity.this);
        }
        @Override
        public void onFinish() {
            loaderView.setVisibility(View.INVISIBLE);

        }
    };

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Item_id", "");

    }
}
