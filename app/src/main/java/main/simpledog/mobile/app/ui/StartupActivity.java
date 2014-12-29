package main.simpledog.mobile.app.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import main.simpledog.mobile.app.R;

public class StartupActivity extends ActionBarActivity {

    Button getStartedButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.applicationTitle);
        setContentView(R.layout.startup_screen);
        getStartedButton = (Button)findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(getStartedListener);
    }
    View.OnClickListener getStartedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(StartupActivity.this,HomeActivity.class);
            startActivity(i);
        }
    };


}
