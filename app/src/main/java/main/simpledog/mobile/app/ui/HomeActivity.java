package main.simpledog.mobile.app.ui;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.fragments.CategoriesListFragment;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemPagerFragment;

public class HomeActivity extends FragmentActivity implements
        ListItemFragment.OnItemSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDefaultFragment();
        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);
    }

    /** http://stackoverflow.com/questions/13086840/actionbar-up-navigation-with-fragments */
    FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
            if(stackHeight > 0){
                getActionBar().setHomeButtonEnabled(true);
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }else {
                getActionBar().setHomeButtonEnabled(false);
                getActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }
        return true;
    }

    public void initDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =  fragmentManager.findFragmentById(R.id.listViewContainer);
        if(fragment == null){
            fragment = new CategoriesListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.listViewContainer, fragment, CategoriesListFragment.TAG_ID)
                    .commit();
        }

    }


    @Override
    public void itemSelected(int position) {
        ListItemPagerFragment pagerFragment = (ListItemPagerFragment) getSupportFragmentManager().findFragmentByTag(ListItemPagerFragment.TAG_ID);


        ListItemAdapter itemAdapter = ((ListItemFragment) getSupportFragmentManager()
                .findFragmentByTag(ListItemFragment.TAG_ID)).getAdapter();

        if(pagerFragment == null ||
                itemAdapter.getCount() != pagerFragment.getItemsAdapter().getCount()){
            /** Creating a new fragment if fragment either is null or number of items doesn't match to the items in list fragment  */
            Bundle args = new Bundle();
            args.putInt(ItemDetailsFragment.POSITION,position);

            pagerFragment = new ListItemPagerFragment();
            pagerFragment.setArguments(args);
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.itemPagerContainer, pagerFragment, ListItemPagerFragment.TAG_ID)
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.commit();
        }else {
            /** if ok then just pass data of clicked item to a details fragment  */
            // http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
            pagerFragment.getArguments().putInt(ItemDetailsFragment.POSITION,position);
        }
    }



}
