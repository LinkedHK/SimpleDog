package main.simpledog.mobile.app.ui.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadIndicator {

    protected ProgressBar loaderView;
    protected  Activity loaderActivity;

    public LoadIndicator(Activity activity, int resource){
        loaderActivity = activity;
        loaderView = (ProgressBar) activity.findViewById(resource);
    }
    public void hide(){
        loaderView.setVisibility(View.INVISIBLE);
    }
    public void show(){
        loaderView.setVisibility(View.INVISIBLE);
    }


}
