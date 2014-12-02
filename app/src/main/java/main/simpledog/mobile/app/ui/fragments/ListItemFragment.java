package main.simpledog.mobile.app.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
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

import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;

import java.util.List;


public class ListItemFragment extends ListFragment {

    protected ProgressBar loaderView;

    protected ListItemAdapter adapter;

    protected ListItemLoader itemLoader = new ListItemLoader();

    public static final String TAG_ID = "list_item";
    public static final String CATEGORY_ID = "category";

    private   ListItemFragment.OnItemSelectedListener mItemSelectedListener;


    public void onViewCreated (View view, Bundle savedInstanceState) {
        loaderView = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        itemLoader.loadItems(0, getArguments().getString(CATEGORY_ID), loadItemsCallback);

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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mItemSelectedListener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListener");
        }
    }
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
                ListItemPagerFragment listItemPager = (ListItemPagerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ListItemPagerFragment.TAG_ID);
                if(listItemPager != null){
                    /** Notify about new Data */
                    listItemPager.getmViewPager().getAdapter().notifyDataSetChanged();
                }
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

        mItemSelectedListener.itemSelected(position);

    }

    public interface OnItemSelectedListener{
        void itemSelected(int position);
    }

    public ListItemAdapter getAdapter() {
        return adapter;
    }
}
