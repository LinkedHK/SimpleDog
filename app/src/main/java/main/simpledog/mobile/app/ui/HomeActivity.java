package main.simpledog.mobile.app.ui;


import android.support.v4.app.Fragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;

public class HomeActivity extends SingleFragmentActivity implements ListItemFragment.OnItemSelectedListener{

    @Override
    protected Fragment createFragment() {
        setFragment_tag(ListItemFragment.TAG_ID);
        return new ListItemFragment();
    }
    @Override
    public void itemSelected(String item_id) {

    }
}
