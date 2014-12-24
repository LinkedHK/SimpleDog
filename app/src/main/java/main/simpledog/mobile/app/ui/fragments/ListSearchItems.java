package main.simpledog.mobile.app.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.MainSearchActivity;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListSearchItems extends AbstractListFragment implements  Refreshable{

    public static final String TAG_ID = "item_search_list";
    public Context mContext;
    public static ListSearchItems newInstance(Bundle args, Context context){
        ListSearchItems fragment = new ListSearchItems();
        fragment.setContext(context);
        fragment.setArguments(args);
        return  fragment;
    }
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void onResume(){
        super.onResume();
    }

    public void setTitle(){
        String def_list_name = getString(R.string.search_results);
        getActivity().getActionBar().setTitle(def_list_name);
    }
    public Bundle getParams(){
        String s = getArguments().getString(ParamsFactory.SEARCH);
        return  ParamsFactory.setSearchable(s,getPage());
    }
    @Override
    public String getFragmentId() {
        return TAG_ID;
    }
}
