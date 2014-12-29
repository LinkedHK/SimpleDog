package main.simpledog.mobile.app.ui.fragments;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.core.ParamsFactoryConstants;


public class ListItemFragment extends AbstractListFragment {
    public static final String TAG_ID = "list_item";

    public String getCategoryId() {
        return getArguments().getString(ParamsFactoryConstants.CATEGORY_ID);
    }

    public String getCategoryName() {
        return getArguments().getString(ParamsFactoryConstants.CATEGORY_NAME);
    }
    public int getItemNum(){
        return getArguments().getInt(ParamsFactoryConstants.ITEM_NUM);
    }

    @Override
    public Bundle getParams() {
        return ParamsFactory.setBrowseable(getCategoryId(),getCategoryName(),getPage(),getItemNum());
    }
    @Override
    public void setTitle() {
        String cat = getCategoryName();
        String def_list_name = getString(R.string.default_jobs_list);
        ((ActionBarActivity)getActivity()).setTitle(cat + " " + def_list_name);
    }
    @Override
    public String getFragmentId() {
        return TAG_ID;
    }

}
