package main.simpledog.mobile.app.ui.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.ItemPagerActivity;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class AbstractListFragment extends ListFragment implements Refreshable {

    protected ProgressBar progressBar;

    protected ListItemAdapter listItemAdapter;

    protected   ListItemPagerFragment listItemPager;

    private ParamsFactory paramsFactory;

    protected int page = 1;

    private ItemDialogs dialogs;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialogs = new ItemDialogs(getActivity());
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        // Should be abstract
        progressBar = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        setTitle();
        searchItems(new Finishable() {
            @Override
            public void onDone() {

            }
        });
        getListView().setOnScrollListener(scrollItemListener);
    }
    public void searchItems(final Finishable finishable){
        getParamsFactory().updatePage(getPage(),getArguments());
        ItemResolverClient.get(paramsFactory.getUrl(), paramsFactory.getRequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Type type = new TypeToken<ArrayList<Item>>() {
                }.getType();
                ArrayList<Item> items_set = (new GsonBuilder().create().fromJson(response.toString(), type));
                if (listItemAdapter == null) {
                    listItemAdapter = new ListItemAdapter(getActivity(), items_set);
                    listItemAdapter.notifyDataSetChanged();
                    setListAdapter(listItemAdapter);
                } else {
                    listItemAdapter.addEntriesToBottom(items_set);
                }
                //  updatePager();
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                dialogs.itemLoadFailure();

            }

            public void onFailure(int statusCode, Header[] headers, String rs, Throwable errorResponse) {
                dialogs.itemLoadFailure();
            }
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                finishable.onDone();
            }
        });
    }

    ScrollItemListener scrollItemListener = new ScrollItemListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            setPage(page);
            searchItems(new Finishable() {
                @Override
                public void onDone() {

                }
            });
        }
    };
    public void onListItemClick(ListView l, View v, int position, long id) {

        /** Creating a new fragment if fragment either is null or number of items doesn't match to the items in list fragment  */
        /*
        Intent i = new Intent(getActivity(), ItemPagerActivity.class);
        i.putExtras(getArguments());
        getActivity().startActivity(i);

        */

            Bundle args = new Bundle();
            args.putInt(ItemDetailsFragment.POSITION,position);
            args.putString(ListItemPagerFragment.FRAGMENT_ID,getFragmentId());
            ListItemPagerFragment  pagerFragment = new ListItemPagerFragment();
            pagerFragment.setArguments(args);
            FragmentTransaction transaction =
                    getActivity(). getSupportFragmentManager().beginTransaction()
                            .replace(R.id.itemPagerContainer, pagerFragment, getPagerTag())
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(getPagerTag());
        transaction.commit();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_list, container, false);
    }

    public void refreshView(Finishable finishable) {
        synchronized (this){
            if(getAdapter() != null){
                getAdapter().clear();
            }
            setPage(0);
            searchItems(finishable);
        }
    }
    public void onResume(){
        super.onResume();
        setTitle();
    }



    public ParamsFactory getParamsFactory() {
        if(paramsFactory == null){
            paramsFactory = new ParamsFactory(getParams());
        }
        return paramsFactory;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public String getPagerTag(){
        return  ListItemPagerFragment.FRAGMENT_ID + "" + getFragmentId();
    }

    public ArrayAdapter getAdapter() {
        return listItemAdapter;
    }


    public ListItemAdapter getListItemAdapter() {
        return listItemAdapter;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }


    public abstract Bundle getParams();

    public abstract void setTitle();

    public abstract String getFragmentId();
}
