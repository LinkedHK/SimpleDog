package main.simpledog.mobile.app.ui.fragments;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.listeners.ScrollItemListener;

public abstract class AbstractListFragment extends ListFragment implements Refreshable {

    protected ProgressBar progressBar;

    protected ListItemAdapter listItemAdapter;
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        // Should be abstract
        progressBar = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        setTitle();
        searchItems(0, new Finishable() {
            @Override
            public void onDone() {

            }
        });
        getListView().setOnScrollListener(scrollItemListener);
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        ListItemPagerFragment pagerFragment = (ListItemPagerFragment) getActivity(). getSupportFragmentManager().findFragmentByTag(getPagerTag());
        if(pagerFragment == null ||
                getAdapter().getCount() != pagerFragment.getItemsAdapter().getCount()){
            /** Creating a new fragment if fragment either is null or number of items doesn't match to the items in list fragment  */
            Bundle args = new Bundle();
            args.putInt(ItemDetailsFragment.POSITION,position);
            args.putString(ListItemPagerFragment.FRAGMENT_ID,getFragmentId());
            pagerFragment = new ListItemPagerFragment();
            pagerFragment.setArguments(args);
            FragmentTransaction transaction =
                    getActivity(). getSupportFragmentManager().beginTransaction()
                            .replace(R.id.itemPagerContainer, pagerFragment, getPagerTag())
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            /** if ok then just pass data of clicked item to a details fragment  */
            // http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
            pagerFragment.getArguments().putInt(ItemDetailsFragment.POSITION,position);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_list, container, false);
    }

    public void refreshView(Finishable finishable) {
        if(getAdapter() != null){
            getAdapter().clear();
        }
        searchItems(0, finishable);
    }

    public void onResume(){
        super.onResume();
        setTitle();
    }

    public abstract void searchItems(int page, Finishable finishable);


    public abstract void setTitle();


    public String getPagerTag(){
        return  ListItemPagerFragment.FRAGMENT_ID + "" + getFragmentId();
    }

    public abstract String getFragmentId();

    public ArrayAdapter getAdapter() {
        return listItemAdapter;
    }


    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
