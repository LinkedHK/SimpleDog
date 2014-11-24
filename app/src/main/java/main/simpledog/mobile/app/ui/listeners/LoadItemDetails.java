package main.simpledog.mobile.app.ui.listeners;


import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import main.simpledog.mobile.app.ui.fragments.ItemDetailsFragment;

public class LoadItemDetails  extends WebViewClient{

    ItemDetailsFragment fragment;

    LoadItemDetails(ItemDetailsFragment fr){
        fragment = fr;
    }


    public void onPageStarted(WebView view, String url, Bitmap favicon) {



    }



}
