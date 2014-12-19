package main.simpledog.mobile.app.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.core.RecentItemsSuggestionProvider;
import main.simpledog.mobile.app.ui.fragments.ListSearchItems;

public class MainSearchActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_home);
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
            args.putString(ListSearchItems.QUERY, query);
            ListSearchItems  fragment = ListSearchItems.newInstance(args,this);
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.listViewContainer, fragment, ListSearchItems.TAG_ID)
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
     //     transaction.addToBackStack(ListSearchItems.TAG_ID);
            transaction.commit();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options,menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.liveSearchMenu).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount()-1;
        if(count <= 0){
            finish();
            // finish() return to startup screen
        }else {
            super.onBackPressed();
        }
    }

}
