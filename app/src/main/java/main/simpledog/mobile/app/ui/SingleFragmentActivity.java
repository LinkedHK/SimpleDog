package main.simpledog.mobile.app.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import main.simpledog.mobile.app.R;

public abstract class SingleFragmentActivity extends FragmentActivity {


    public String fragment_tag = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =  fragmentManager.findFragmentById(R.id.listViewContainer);
        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.listViewContainer,fragment,getFragment_tag())
                    .commit();
        }
    }

    protected abstract Fragment createFragment();

    public String getFragment_tag() {
        return fragment_tag;
    }

    public void setFragment_tag(String fragment_tag) {
        this.fragment_tag = fragment_tag;
    }
}

