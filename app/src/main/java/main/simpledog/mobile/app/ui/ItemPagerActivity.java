package main.simpledog.mobile.app.ui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.core.ListItemLoader;
import main.simpledog.mobile.app.ui.core.ParamsFactory;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import main.simpledog.mobile.app.ui.fragments.Finishable;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemPagerActivity extends FragmentActivity  {
    private ProgressBar progressBar;
    private ViewPager mViewPager;
    private ArrayList<Item> itemsList;
    private ListItemPagerAdapter pagerAdapter;
    protected ItemDialogs dialogs = new ItemDialogs(this);
    ParamsFactory paramsFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.item_list_pager);
        //progressBar = (ProgressBar)findViewById(R.id.progressBar);
        paramsFactory = new ParamsFactory(getIntent().getExtras());
        mViewPager = new ViewPager(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        loadItems(new Finishable() {
            @Override
            public void onDone() {
                pagerAdapter = new ListItemPagerAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(pagerAdapter);
            }
        });
        // Get page 0

        // Search
        // Browse BY
        // Get item position . set current item = item position
    }
    public void loadItems(final Finishable finishable){
        ItemResolverClient.get(paramsFactory.getUrl(), paramsFactory.getRequestParams(), ParamsFactory.TIMEOUT, new JsonHttpResponseHandler() {
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Type type = new TypeToken<ArrayList<Item>>() {
                }.getType();
                itemsList = (new GsonBuilder().create().fromJson(response.toString(), type));
                finishable.onDone();
            }
            public final void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialogs.itemLoadFailure();
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                dialogs.itemLoadFailure();
            }
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                finishable.onDone();
            }

        });
    }
    public ArrayList<Item> getItemsList() {
        return itemsList;
    }
    private class  ListItemPagerAdapter extends FragmentStatePagerAdapter{
        ListItemPagerAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            Item item = getItemsList().get(position);
            return ItemDetailsFragment.newInstance(item.id, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position >= getCount()) {
                FragmentManager manager = ((Fragment) object).getFragmentManager();
                manager.beginTransaction().remove((Fragment) object).commit();
            }
        }
        @Override
        public int getCount() {
            return getItemsList().size();
        }
    }



}
