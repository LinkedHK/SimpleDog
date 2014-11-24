package main.simpledog.mobile.app.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
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

import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;

import java.util.List;


public class ListItemFragment extends ListFragment {

    protected ProgressBar loaderView;

   private  ListItemAdapter adapter;

    private ListItemLoader itemLoader = new ListItemLoader();

    OnItemSelectedListener mItemSelectedListener;


    public static final String TAG_ID = "list_item_tag";

    public void onViewCreated (View view, Bundle savedInstanceState) {
        loaderView = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        itemLoader.loadItems(0,null,loadItemsCallback);
        getListView().setOnScrollListener(scrollItemListener);

    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mItemSelectedListener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListener");
        }
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
        showItem(item.id,position);
    }

    void showItem(String item_id, int position){
        // We can display everything in-place with fragments, so update
        // the list to highlight the selected item and show the data.
        getListView().setItemChecked(position,true);

        // Check what fragment is currently shown, replace if needed.
        ItemDetailsFragment detailsFragment =(ItemDetailsFragment) getFragmentManager().findFragmentById(R.id.itemDetailsContainer);


        if(detailsFragment == null || detailsFragment.getShowIndex() != position){
            detailsFragment =  ItemDetailsFragment.newInstance(item_id,position);

            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.itemDetailsContainer,detailsFragment,ItemDetailsFragment.TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();

        }

    }
    public ListItemAdapter getAdapter() {
        return adapter;
    }

    public interface OnItemSelectedListener{
        void itemSelected(String item_id);
    }


}
