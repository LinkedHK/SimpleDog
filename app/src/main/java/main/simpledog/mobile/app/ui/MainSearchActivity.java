package main.simpledog.mobile.app.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.core.RecentItemsSuggestionProvider;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.fragments.ListSearchItemsFrafment;

public class MainSearchActivity extends AbstractMenuActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_home);
        super.buildPostView(savedInstanceState);
        handleIntent(getIntent());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Log.d("search","Search clicked!!");
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentItemsSuggestionProvider.AUTHORITY,RecentItemsSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            /** Creating a new fragment if fragment either is null or number
             * of items doesn't match to the items in the list fragment  */
            Bundle args = new Bundle();
            args.putString(ParamsFactory.SEARCH, query);
            ListSearchItemsFrafment fragment = ListSearchItemsFrafment.newInstance(args, this);
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.listViewContainer, fragment, ListSearchItemsFrafment.TAG_ID)
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          transaction.addToBackStack(ListSearchItemsFrafment.TAG_ID);
            transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if(count <=1 ){
                    finish();
                }else {
                    super.onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
