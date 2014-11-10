package main.simpledog.mobile.app;

import android.app.Activity;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import com.robotium.solo.Solo;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.ListItemActivity;

public class ListItemActivityTest extends ActivityInstrumentationTestCase2<ListItemActivity> {


    private Solo solo;
    private ListItemActivity listItemactivity;

    public ListItemActivityTest(){
        super(ListItemActivity.class);
    }
    public  void  setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
        listItemactivity = getActivity();
    }

    public void testShowView() throws InterruptedException{
        Thread.sleep(20000);
    }

    public  void testFailureToLoad(){
        solo.waitForDialogToOpen();
        listItemactivity.setItems_url("/noneexisting-url");
        solo.searchText(listItemactivity.getResources().getString( R.string.item_load_failure_title));
    }


}