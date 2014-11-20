package main.simpledog.mobile.app.ui.fragments;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.ListItemLoader;

import java.util.List;

public class ShowItemFragment  extends Fragment{

    TextView itemTitle;

    TextView itemDescription;

    int item_id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.show_item_fragment, container, false);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       new ListItemLoader().showItem(item_id,showItemCallback);
    }

    ListItemLoader.LoadItemsInterface showItemCallback = new ListItemLoader.LoadItemsInterface() {
        @Override
        public void onStart() {

        }
        @Override
        public void onSuccess(int statusCode, List<Item> items) {

        }

        @Override
        public void onFailure(int statusCode) {

        }

        @Override
        public void onFinish() {

        }
    };





}

