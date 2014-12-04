package main.simpledog.mobile.app.ui.fragments;



import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.ItemCategory;
import main.simpledog.mobile.app.ui.ListItemLoader;
import main.simpledog.mobile.app.ui.adapters.ListCategoriesAdapter;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class CategoriesListFragment extends ListFragment implements  Refreshable {

  protected   ListCategoriesAdapter categoryListAdapter;
    protected ProgressBar loaderView;


    public static final String TAG_ID = "categories_list";


    public void onActivityCreated( Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderView = (ProgressBar) getActivity().findViewById(R.id.itemLoadingProgressBar);
        loadCategories();
    }
    private void loadCategories(){
        ItemResolverClient.get(ListItemLoader.item_cat_path, null,ListItemLoader.loadItemTimeout, new JsonHttpResponseHandler(){
            public void onStart() {
                getLoaderView().setVisibility(View.VISIBLE);
            }
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Type type = new TypeToken<ArrayList<ItemCategory>>(){}.getType();
                ArrayList<ItemCategory> items_set  = (new GsonBuilder().create().fromJson(response.toString(),type));
                categoryListAdapter  = new ListCategoriesAdapter(getActivity(),items_set);
                categoryListAdapter.notifyDataSetChanged();
                setListAdapter(categoryListAdapter);

            }
            public final void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ItemDialogs.itemLoadFailure(getActivity());

            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ItemDialogs.itemLoadFailure(getActivity());
            }
            public void onFinish() {
                getLoaderView().setVisibility(View.INVISIBLE);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        setTitle();
    }
    public  void setTitle(){
            getActivity().getActionBar()
                    .setTitle(R.string.page_categories_jobs);

    }

    @Override
    public void refreshView() {
        loadCategories();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setTitle();

    }


    public void onListItemClick(ListView l, View v, int position, long id) {
        ItemCategory itemCategory = getCategoryListAdapter().getItem(position);
        showItems(String.valueOf( itemCategory.id),itemCategory.name,itemCategory.items_count);
    }

    private void showItems(String cat_id,String cat_name, int item_num){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ListItemFragment fragment =  (ListItemFragment) fragmentManager.findFragmentByTag(ListItemFragment.TAG_ID);
        if(fragment == null){
            fragment = new ListItemFragment();
            fragment.setParams(cat_id, cat_name, item_num);
            fragmentManager.beginTransaction()
                    .replace(R.id.viewContainer, fragment, ListItemFragment.TAG_ID)
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(CategoriesListFragment.TAG_ID)
                    .commit();
        }else {
            /**
             * Just replace args If fragment already exists
             * http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
             * */
            fragment.updateParams(cat_id,cat_name,item_num);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_button_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                // Refreshing page
                loadCategories();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_categories_list, container, false);
    }

    public ProgressBar getLoaderView() {
        return loaderView;
    }


    public ListCategoriesAdapter getCategoryListAdapter() {
        return categoryListAdapter;
    }



}
