package main.simpledog.mobile.app.ui;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;


public class ItemListPagerActivity  extends FragmentActivity {


    private ViewPager mViewPager;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_pager);
        mViewPager = new ViewPager(this);

        String item_id  = getIntent().getStringExtra(ItemDetailsFragment.ITEM_ID);
        int position = getIntent().getIntExtra(ItemDetailsFragment.POSITION, 0);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });
        ListItemFragment fragment =(ListItemFragment) getSupportFragmentManager().findFragmentByTag(ListItemFragment.TAG_ID);

    }



}
