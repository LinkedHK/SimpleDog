package main.simpledog.mobile.app;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.robotium.solo.Solo;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;

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
        solo.waitForFragmentById(R.id.listViewContainer);
        ListItemFragment listItemFragment = (ListItemFragment)
                getActivity().getSupportFragmentManager()
                .findFragmentByTag(ListItemFragment.TAG_ID);
        assertEquals(true,listItemFragment.isVisible());
        Thread.sleep(2000);
        assertNotNull(listItemFragment.getAdapter());
        solo.clickInList(1);
        Thread.sleep(10000);

    }
}