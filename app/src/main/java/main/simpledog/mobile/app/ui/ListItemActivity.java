package main.simpledog.mobile.app.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;

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
        itemLoader.loadItems(0,null,loadItemsCallback);
        getListView().setOnScrollListener(scrollItemListener);
    }

    ScrollItemListener scrollItemListener = new ScrollItemListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            itemLoader.loadItems(page,null,loadItemsCallback);
        }
    };
    ListItemLoader.LoadItemsInterface loadItemsCallback  = new ListItemLoader.LoadItemsInterface() {
        @Override
        public void onStart() {
            loaderView.setVisibility(View.VISIBLE);
        }
        @Override
        public void onSuccess(int statusCode, List<Item> items) {
            if(adapter == null){
                adapter = new ListItemAdapter(ListItemActivity.this,items);
                adapter.notifyDataSetChanged();
                setListAdapter(adapter);

            }else {
                adapter.addEntriesToBottom(items);
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
