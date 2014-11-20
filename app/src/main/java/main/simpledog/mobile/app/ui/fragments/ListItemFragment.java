package main.simpledog.mobile.app.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.ListItemLoader;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import main.simpledog.mobile.app.ui.fragments.ShowItemFragment;
import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;

import java.util.List;


public class ListItemFragment extends ListFragment {


    protected ProgressBar loaderView;

   private  ListItemAdapter adapter;

    private ListItemLoader itemLoader = new ListItemLoader();

    private ViewPager mViewPager;


    public void onViewCreated (View view, Bundle savedInstanceState) {
        loaderView = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        itemLoader.loadItems(0,null,loadItemsCallback);
        getListView().setOnScrollListener(scrollItemListener);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_list, container, false);
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
                adapter = new ListItemAdapter(getActivity(),items);
                adapter.notifyDataSetChanged();
                setListAdapter(adapter);
            }else {
                adapter.addEntriesToBottom(items);
            }
        }
        @Override
        public void onFailure(int statusCode) {
            ItemDialogs.itemLoadFailure(getActivity());
        }
        @Override
        public void onFinish() {
            loaderView.setVisibility(View.INVISIBLE);
        }
    };


    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item) getListAdapter().getItem(position);
        Log.d("item_clicked" , " " + item.id);
    }
}
