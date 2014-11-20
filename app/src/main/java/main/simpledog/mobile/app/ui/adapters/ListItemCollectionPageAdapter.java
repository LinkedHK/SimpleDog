package main.simpledog.mobile.app.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

public  class ListItemCollectionPageAdapter extends FragmentStatePagerAdapter {

    ListItemCollectionPageAdapter(android.support.v4.app.FragmentManager fragmentManager){
        super(fragmentManager);

    }
    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
