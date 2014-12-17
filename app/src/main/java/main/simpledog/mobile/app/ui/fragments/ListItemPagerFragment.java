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

    FragmentStatePagerAdapter fragmentStatePagerAdapter;

    public static final String TAG_ID = "list_item_pager";
    public static final String FRAGMENT_ID = "fragment_id";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        String fragment_id = getArguments().getString(FRAGMENT_ID);
        AbstractListFragment fragment = (AbstractListFragment) getActivity().getSupportFragmentManager().findFragmentByTag(fragment_id);
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
    public void onDetach(){
        super.onDetach();
    }
    public void onResume(){
        super.onResume();
        if(fragmentStatePagerAdapter != null){
            fragmentStatePagerAdapter.notifyDataSetChanged();
        }

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       MenuItem item = menu.findItem(R.id.refresh_button);
     //   MenuItem search = menu.findItem(R.id.liveSearchMenu);
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


    public FragmentStatePagerAdapter getPagerAdapter() {
        return fragmentStatePagerAdapter;
    }
}
