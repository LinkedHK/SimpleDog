package main.simpledog.mobile.app.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.ArrayAdapter;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;

public class ListItemPagerFragment extends Fragment {

    private ViewPager mViewPager;

    private ArrayAdapter<Item> itemsAdapter;

    public static final String TAG_ID = "list_item_pager";



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListItemFragment fragment = (ListItemFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ListItemFragment.TAG_ID);
        itemsAdapter = fragment.getAdapter();
        mViewPager = (ViewPager) getView().findViewById(R.id.pager);
        mViewPager.setAdapter(new ListItemPagerAdapter(getActivity().getSupportFragmentManager()));
        mViewPager.setCurrentItem(getArguments().getInt(ItemDetailsFragment.POSITION));

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_list_pager, container,
                false);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       MenuItem item = menu.findItem(R.id.refresh_button);
        if(item != null){
            item.setVisible(false);
        }

        super.onCreateOptionsMenu(menu,inflater);
    }
   private class  ListItemPagerAdapter extends FragmentStatePagerAdapter{
       ListItemPagerAdapter(FragmentManager manager){
           super(manager);
       }
       @Override
       public Fragment getItem(int position) {
           Item item = getItemsAdapter().getItem(position);
           return ItemDetailsFragment.newInstance(item.id,position);
       }
       @Override
       public int getCount() {
           return getItemsAdapter().getCount();
       }
   }

    public ArrayAdapter<Item> getItemsAdapter() {
        return itemsAdapter;
    }
    public ViewPager getmViewPager() {
        return mViewPager;
    }
}
