package main.simpledog.mobile.app.ui.fragments;


import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListSearchItems extends ListItemFragment {

    public static final String TAG_ID = "list_search_list";
    public static final String QUERY = "search";
    public static final String URL = "/live_search";
    private RequestParams params;

    /**
     *
     * Init Menu
     * Search Items
     * if search is empty display nothing found
     *
     */

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        loaderView = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        setTitle();

        searchItems(0, new Finishable() {
            @Override
            public void onDone() {

            }
        });
        getListView().setOnScrollListener(scrollItemListener);
    }
    public void searchItems(int page, final Finishable finishable){
        RequestParams p =  getParams();
        p.put("page", page);
        ItemResolverClient.get(URL, p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Type type = new TypeToken<ArrayList<Item>>() {
                }.getType();
                ArrayList<Item> items_set = (new GsonBuilder().create().fromJson(response.toString(), type));
                if(items_set.size() == 0)  return;
                adapter = new ListItemAdapter(getActivity(), items_set);
                adapter.notifyDataSetChanged();
                setListAdapter(adapter);
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ((HomeActivity) getActivity()).getItemDialogs().itemLoadFailure();
            }
            public void onFailure(int statusCode,  Header[] headers, String rs, Throwable errorResponse){
                ((HomeActivity) getActivity()).getItemDialogs().itemLoadFailure();
            }
            public void onFinish() {
                getLoaderView().setVisibility(View.INVISIBLE);
                finishable.onDone();
            }
        });
    }

    ScrollItemListener scrollItemListener = new ScrollItemListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            searchItems(page, new Finishable() {
                @Override
                public void onDone() {

                }
            });
        }
    };
    public void setTitle(){
        String def_list_name = getString(R.string.search_results);
        getActivity().getActionBar().setTitle(def_list_name);
    }
    public void refreshView(Finishable finishable) {
        getAdapter().clear();
        searchItems(0, finishable);
    }
    public RequestParams getParams(){
        if(params == null){
            params = new RequestParams();
            String s = getArguments().getString(QUERY);
            params.put(QUERY,s);
        }
        return  params;

    }
}
