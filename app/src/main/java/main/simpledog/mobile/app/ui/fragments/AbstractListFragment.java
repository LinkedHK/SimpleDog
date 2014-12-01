package main.simpledog.mobile.app.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.ListItemLoader;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;

public abstract class AbstractListFragment extends ListFragment {

    protected ProgressBar loaderView;

    protected ListItemAdapter adapter;

    protected ListItemLoader itemLoader = new ListItemLoader();


    public void onViewCreated (View view, Bundle savedInstanceState) {
        loaderView = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);

    }


    public ListItemAdapter getAdapter() {
        return adapter;
    }

    public ListItemLoader getItemLoader() {
        return itemLoader;
    }

    public void setItemLoader(ListItemLoader itemLoader) {
        this.itemLoader = itemLoader;
    }
}
