package main.simpledog.mobile.app.ui.core;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import main.simpledog.mobile.app.ui.fragments.Finishable;
import main.simpledog.mobile.app.ui.fragments.Refreshable;
import main.simpledog.mobile.app.ui.utils.FragmentUtil;


public class RefreshManager  {


    protected boolean refreshing = false;

    protected Context mContext;

    public RefreshManager(Context context){
        mContext = context;
    }


    public void refreshFragment(){
        synchronized (this){
            if(!refreshing){
                refreshing = true;
            }else {
                return;
            }
            Fragment fragment = FragmentUtil.getCurrentFragment((FragmentActivity)getContext());
            if(fragment != null){
                try {
                    Refreshable refreshable = (Refreshable)fragment;
                    refreshable.refreshView(new Finishable() {
                        @Override
                        public void onDone() {
                            refreshing = false;
                        }
                    });
                }catch (ClassCastException e){
                    refreshing = false;
                }
            }else {
                refreshing = false;
            }
        }
    }

    public Context getContext() {
        return mContext;
    }
}
