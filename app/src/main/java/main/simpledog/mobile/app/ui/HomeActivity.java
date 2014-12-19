package main.simpledog.mobile.app.ui;



import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.core.RecentItemsSuggestionProvider;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import main.simpledog.mobile.app.ui.fragments.*;
import main.simpledog.mobile.app.ui.utils.FragmentUtil;

import java.util.List;

public class HomeActivity extends FragmentActivity {

    ItemDialogs itemDialogs;

    protected boolean refreshing = false;

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
        //    f.toString();
            Log.d("Current_Fragment", FragmentUtil.getCurrentFragment(HomeActivity.this).toString());
            Log.d("Fragments", getSupportFragmentManager().getFragments().toString());
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
            synchronized (this){
                if(!refreshing){
                    refreshing = true;
                }else {
                    return;
                }
                Fragment fragment = FragmentUtil.getCurrentFragment(this);
                if(fragment != null){
                    try {
                        Refreshable refreshable = (Refreshable)fragment;
                        refreshable.refreshView(new Finishable() {
                            @Override
                            public void onDone() {
                                refreshing = false;
                            }
                        });
                    }catch (ClassCastException e){
                        refreshing = false;
                    }
                }else {
                    refreshing = false;
                }
            }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options,menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       SearchView searchView =
                (SearchView) menu.findItem(R.id.liveSearchMenu).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
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
    public ItemDialogs getItemDialogs() {
        if(itemDialogs == null){
            itemDialogs = new ItemDialogs(this);
        }
        return itemDialogs;
    }
}
