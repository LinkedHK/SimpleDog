package main.simpledog.mobile.app.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.core.ParamsFactory;

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
