package main.simpledog.mobile.app.ui.fragments;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.*;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.ItemResolverClient;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.rest.entities.ShowItem;
import main.simpledog.mobile.app.ui.ListItemLoader;
import main.simpledog.mobile.app.ui.dialogs.ItemDialogs;

import java.util.List;

public class ItemDetailsFragment extends Fragment {

    public final static String ITEM_ID = "item_id";
    public final static String POSITION = "position";
    public final static String TAG_ID = "item_details";

   private TextView itemTitle;
    private TextView itemDescription;
    private ListItemLoader itemLoader;
    private ProgressBar progressBar;
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
       // setHasOptionsMenu(true); // Show options

        itemTitle = (TextView) getView().findViewById(R.id.itemDetailsTitle);
        itemDescription = (TextView) getView().findViewById(R.id.itemDetailsDescription);
        progressBar = (ProgressBar) getView().findViewById(R.id.showDetailsLoad);
        itemLoader = new ListItemLoader();
        itemLoader.showItem(getItemId(), new ListItemLoader.LoadItemsInterface() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSuccess(int statusCode, List<Item> items) {
                ShowItem item = (ShowItem)items.get(0);
                itemTitle.setText(item.title);
                itemDescription.setText(item.description);
            }
            @Override
            public void onFailure(int statusCode) {
                ItemDialogs.itemLoadFailure(getActivity());
            }
            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_details, container, false);
    }

    public static ItemDetailsFragment newInstance(String item_id, int position){
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION,position);
        args.putString(ITEM_ID, item_id);
        fragment.setArguments(args);
        return  fragment;
    }


    public String getItemId() {
        return getArguments().getString(ITEM_ID);
    }


}
