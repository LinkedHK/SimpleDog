package main.simpledog.mobile.app.ui.utils;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FragmentUtil {

    public static Fragment getCurrentFragment(FragmentActivity activity){
        Fragment fragment = null;
        int count = activity.getSupportFragmentManager().getBackStackEntryCount();
        if(count > 0){
            fragment = activity.getSupportFragmentManager().getFragments().get(count-1);
        }
        return  fragment;
    }
}
