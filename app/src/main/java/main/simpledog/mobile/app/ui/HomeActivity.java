package main.simpledog.mobile.app.ui;


import android.support.v4.app.Fragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ListItemFragment();
    }
}
