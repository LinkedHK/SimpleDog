package main.simpledog.mobile.app.ui;

import android.app.ListActivity;
import android.os.Bundle;


import main.simpledog.mobile.app.R;

public class ListItemActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

    }



}
