package main.simpledog.mobile.app.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.fragments.*;
import main.simpledog.mobile.app.ui.utils.FragmentUtil;

public class HomeActivity extends AbstractMenuActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.buildPostView(savedInstanceState);
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
                getSupportActionBar().setHomeButtonEnabled(has_stack);
                getSupportActionBar().setDisplayHomeAsUpEnabled(has_stack);
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

    private void initDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =  fragmentManager.findFragmentById(R.id.listViewContainer);
        if(fragment == null){
            Bundle bundle = ParamsFactory.setListCategories();
            fragment = new CategoriesListFragment();
            fragment.setArguments(bundle);
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

}
