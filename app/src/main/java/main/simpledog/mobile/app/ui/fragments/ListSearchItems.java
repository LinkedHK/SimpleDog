package main.simpledog.mobile.app.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListSearchItems extends AbstractListFragment implements  Refreshable{

    public static final String TAG_ID = "item_search_list";
    public static final String QUERY = "search";
    public static final String URL = "/live_search";

    private RequestParams params;



    public void searchItems(int page, final Finishable finishable){
        RequestParams p =  getParams();
        p.put("page", page);
        ItemResolverClient.get(URL, p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Type type = new TypeToken<ArrayList<Item>>() {
                    }.getType();
                    ArrayList<Item> items_set = (new GsonBuilder().create().fromJson(response.toString(), type));

                    if(listItemAdapter == null){
                        listItemAdapter = new ListItemAdapter(getActivity(), items_set);
                        listItemAdapter.notifyDataSetChanged();
                        setListAdapter(listItemAdapter);
                    }else {
                            listItemAdapter.addEntriesToBottom(items_set);
                    }
                updatePager();
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ((HomeActivity) getActivity()).getItemDialogs().itemLoadFailure();
            }
            public void onFailure(int statusCode,  Header[] headers, String rs, Throwable errorResponse){
                ((HomeActivity) getActivity()).getItemDialogs().itemLoadFailure();
            }
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                finishable.onDone();
            }
        });
    }

    public static ListSearchItems newInstance(Bundle args){
        ListSearchItems fragment = new ListSearchItems();
        fragment.setArguments(args);

        return  fragment;

    }



    public void onResume(){
        super.onResume();
    }
    public void setTitle(){
        String def_list_name = getString(R.string.search_results);
        getActivity().getActionBar().setTitle(def_list_name);
    }
    public RequestParams getParams(){
            params = new RequestParams();
            String s = getArguments().getString(QUERY);
            params.put(QUERY,s);
        return  params;

    }
    @Override
    public String getFragmentId() {
        return TAG_ID;
    }
}
