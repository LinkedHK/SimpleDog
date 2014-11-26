package main.simpledog.mobile.app.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;

public abstract class SingleFragmentActivity extends FragmentActivity {



    private ViewPager mViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager = new ViewPager(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =  fragmentManager.findFragmentById(R.id.listViewContainer);
        if(fragment == null){
            fragment = new ListItemFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.listViewContainer, fragment, ListItemFragment.TAG_ID)
                    .commit();


        }

    }

    protected Fragment createFragment() {

        return new ListItemFragment();
    }

}

