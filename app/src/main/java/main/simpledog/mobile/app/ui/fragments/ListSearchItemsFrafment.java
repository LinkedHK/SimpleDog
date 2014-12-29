package main.simpledog.mobile.app.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.core.ParamsFactoryConstants;
import main.simpledog.mobile.app.ui.core.ParamsFactoryParser;

public class ListSearchItemsFrafment extends AbstractListFragment implements  Refreshable{

    public static final String TAG_ID = "item_search_list";
    public Context mContext;
    public static ListSearchItemsFrafment newInstance(Bundle args, Context context){
        ListSearchItemsFrafment fragment = new ListSearchItemsFrafment();
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
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(def_list_name);
    }
    public Bundle getParams(){
        String s = getArguments().getString(ParamsFactoryConstants.SEARCH);
        return  ParamsFactoryParser.setSearchable(s, getPage());
    }
    @Override
    public String getFragmentId() {
        return TAG_ID;
    }
}
