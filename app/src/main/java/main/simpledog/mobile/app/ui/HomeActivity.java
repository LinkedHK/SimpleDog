package main.simpledog.mobile.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import main.simpledog.mobile.app.R;

public class HomeActivity extends Activity {

    TextView helloText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helloText = (TextView)findViewById(R.id.helloText);
        helloText.setOnClickListener(helloClick);
    }
    View.OnClickListener helloClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeActivity.this,ListItemActivity.class);
            startActivity(intent);
        }
    };




}
