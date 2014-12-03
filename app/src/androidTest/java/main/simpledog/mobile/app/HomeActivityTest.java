package main.simpledog.mobile.app;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.robotium.solo.Solo;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.fragments.CategoriesListFragment;
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
        CategoriesListFragment categoriesListFragment = (CategoriesListFragment)
                getActivity().getSupportFragmentManager()
                .findFragmentByTag(CategoriesListFragment.TAG_ID);
        assertEquals(true,categoriesListFragment.isVisible());
        Thread.sleep(2000);
        assertNotNull(categoriesListFragment.getCategoryListAdapter());
        solo.clickInList(1); // Select first Job category
        Thread.sleep(2000);
        solo.clickInList(1);// Select first Job Item
        Thread.sleep(2000);
        solo.clickOnActionBarHomeButton(); // back to List Jobs
        solo.clickOnActionBarHomeButton(); // back to List Categories

    }
}