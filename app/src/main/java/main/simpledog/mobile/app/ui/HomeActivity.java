package main.simpledog.mobile.app.ui;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.ui.adapters.ListItemAdapter;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemPagerFragment;

public class HomeActivity extends SingleFragmentActivity implements ListItemFragment.OnItemSelectedListener{

    @Override
    public void itemSelected(int position) {
        ListItemPagerFragment pagerFragment = (ListItemPagerFragment) getSupportFragmentManager().findFragmentByTag(ListItemPagerFragment.TAG_ID);

        ListItemAdapter itemAdapter = ((ListItemFragment) getSupportFragmentManager()
                .findFragmentByTag(ListItemFragment.TAG_ID)).getAdapter();

        Bundle args = new Bundle();
        args.putInt(ItemDetailsFragment.POSITION,position);
        if(pagerFragment == null ||
                itemAdapter.getCount() != pagerFragment.getItemsAdapter().getCount()){
            pagerFragment = new ListItemPagerFragment();
            pagerFragment.setArguments(args);
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.itemPagerContainer, pagerFragment, ListItemPagerFragment.TAG_ID)
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(ListItemPagerFragment.TAG_ID);
                transaction.commit();
        }else {
            pagerFragment.setArguments(args);
        }
    }
}
