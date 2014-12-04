package main.simpledog.mobile.app.ui;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.fragments.*;

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
    private FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                /** Set Back Button */
                int stackHeight = fragmentManager.getBackStackEntryCount();
                boolean  has_stack = stackHeight > 1;
                getActionBar().setHomeButtonEnabled(has_stack);
                getActionBar().setDisplayHomeAsUpEnabled(has_stack);
                /**
                 *  http://stackoverflow.com/questions/6503189/fragments-onresume-from-back-stack/6505060#6505060
                 *  Call on Resume Fragment Method To update menu
                 *
                 * */
                if(stackHeight > 0){
                    Fragment fragment = fragmentManager.getFragments().get(stackHeight-1);
                    if(fragment != null){
                        fragment.onResume();
                    }
                }
            }
        }
    };
    /**
     * Fix Pop back to previous fragment
     * */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                break;
            case R.id.refresh_button:
                refreshFragment();
                break;
            default:
                return  super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void refreshFragment(){
        int c = getSupportFragmentManager().getBackStackEntryCount();

        if(c > 0){
            Fragment fragment = getSupportFragmentManager().getFragments().get(c-1);


            if(fragment != null){
                try {
                    Refreshable refreshable = (Refreshable)fragment;
                    refreshable.refreshView();
                }catch (ClassCastException e){

                }

            }
        }
    }

    public void initDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =  fragmentManager.findFragmentById(R.id.listViewContainer);
        if(fragment == null){
            fragment = new CategoriesListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.listViewContainer, fragment, CategoriesListFragment.TAG_ID)
                    .addToBackStack(CategoriesListFragment.TAG_ID)
                    .commit();
        }
    }

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
    @Override
    public void onBackPressed() {
       int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count <= 1){
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);

            // finish() return to startup screen
        }else {
            super.onBackPressed();
        }

    }



}
