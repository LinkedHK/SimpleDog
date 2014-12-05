package main.simpledog.mobile.app;

import android.app.Activity;

import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.robotium.solo.Solo;
import main.simpledog.mobile.app.rest.entities.Item;
import main.simpledog.mobile.app.ui.HomeActivity;
import main.simpledog.mobile.app.ui.adapters.ListCategoriesAdapter;
import main.simpledog.mobile.app.ui.fragments.CategoriesListFragment;
import main.simpledog.mobile.app.ui.fragments.ListItemFragment;
import main.simpledog.mobile.app.ui.utils.FragmentUtil;

import java.util.List;

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




    public void testClickableCategoryItems()  throws InterruptedException{
        solo.waitForFragmentById(R.id.listViewContainer);
        Fragment fragment = FragmentUtil.getCurrentFragment(getActivity());
        assertTrue(fragment instanceof CategoriesListFragment);
        Thread.sleep(2000);

        CategoriesListFragment categoriesListFragment = (CategoriesListFragment)fragment;
        assertNotNull(categoriesListFragment.getCategoryListAdapter());
        solo.clickInList(categoriesListFragment.getCategoryListAdapter().getCount()-1); // Click first job category
        Thread.sleep(2000);

        Fragment fragment1 = FragmentUtil.getCurrentFragment(getActivity());
        assertTrue(fragment1 instanceof  ListItemFragment);

    }

}