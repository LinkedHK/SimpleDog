package main.simpledog.mobile.app;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.TextView;
import com.robotium.solo.Solo;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.ListItemActivity;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {


    private Solo solo;
    private Activity homeActivity;

    public  HomeActivityTest(){
        super(HomeActivity.class);
    }

    public  void  setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
        homeActivity = getActivity();
    }
    public void testOpensListActivity() throws InterruptedException{
        String helloText = ((TextView)homeActivity.findViewById(R.id.helloText)).getText().toString();
        solo.clickOnText(helloText);
        solo.assertCurrentActivity("List Activity", ListItemActivity.class);
        Thread.sleep(10000);

    }
}