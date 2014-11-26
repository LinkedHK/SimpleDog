package main.simpledog.mobile.app.ui;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;

public class HomeActivity extends SingleFragmentActivity implements ListItemFragment.OnItemSelectedListener{


    @Override
    public void itemSelected(String item_id, int position) {
        Intent i = new Intent(this, ItemListPagerActivity.class);
        i.putExtra(ItemDetailsFragment.ITEM_ID,item_id);
        i.putExtra(ItemDetailsFragment.POSITION,position);

        startActivity(i);

    }
}
