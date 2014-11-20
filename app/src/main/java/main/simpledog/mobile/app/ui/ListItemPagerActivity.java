package main.simpledog.mobile.app.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;



import java.util.List;

public class ListItemPagerActivity extends FragmentActivity {

    static final int NUM_ITEMS = 10;

    private ViewPager mViewPager;

    private List<Item> mItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

    }




}
