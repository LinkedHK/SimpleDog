package main.simpledog.mobile.app.ui.fragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.core.ListItemLoader;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;

import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;

import java.util.List;

public class ListItemFragment extends AbstractListFragment {




    protected ListItemLoader itemLoader;


    public static final String TAG_ID = "list_item";
    public static final String CATEGORY_ID = "category";
    public static final String CATEGORY_NAME = "category_name";
    public static final String ITEM_NUM = "item_num";

    public void searchItems(int page, final Finishable finishable){
        getItemLoader().loadItems(page, getCurrentCategory(), new ListItemLoader.LoadItemsInterface() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSuccess(int statusCode, List<Item> items) {
                if(items.size() == 0){
                    return; // Show not found Result
                }
                if(listItemAdapter == null){
                    listItemAdapter = new ListItemAdapter(getActivity(),items);
                    listItemAdapter.notifyDataSetChanged();
                    setListAdapter(listItemAdapter);
                }else {
                    listItemAdapter.addEntriesToBottom(items);
                    ListItemPagerFragment listItemPager = (ListItemPagerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ListItemPagerFragment.TAG_ID);
                    if(listItemPager != null){
                        /** Notify about new Data */
                        if(listItemPager.getPagerAdapter() != null){
                            listItemPager.getPagerAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode) {

                ((HomeActivity)getActivity()).getItemDialogs().itemLoadFailure();
            }
            @Override
            public void onFinish() {
                getProgressBar().setVisibility(View.INVISIBLE);
                finishable.onDone();
            }
        });

    }

    public String getCurrentCategory() {
        return getArguments().getString(CATEGORY_ID);
    }
    public void setParams(String cat_id,String cat_name, int item_num  ){
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID,cat_id);
        args.putString(CATEGORY_NAME, cat_name);
        args.putInt(ITEM_NUM, item_num);
        setArguments(args);
    }
    public void updateParams(String cat_id,String cat_name, int item_num  ){
        getArguments().putString(CATEGORY_ID,cat_id);
        getArguments().putString(CATEGORY_NAME, cat_name);
        getArguments().putInt(ITEM_NUM, item_num);
    }

    @Override
    public void setTitle() {
        String cat = getArguments().getString(CATEGORY_NAME);
        String def_list_name = getString(R.string.default_jobs_list);
        getActivity().getActionBar().setTitle(cat + " " + def_list_name);
    }

    @Override
    public String getFragmentId() {
        return TAG_ID;
    }
    public ListItemLoader getItemLoader() {
        if(itemLoader == null){
            itemLoader = new ListItemLoader();
        }
        return itemLoader;
    }
}
