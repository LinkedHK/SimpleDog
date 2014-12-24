package main.simpledog.mobile.app.ui.core;

import android.os.Bundle;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.rest.Routes;

public class ParamsFactory {

    public final static String SEARCH = "search";
    public final static String PAGE = "page";
    public final static String CATEGORY_ID = "category";
    // Used to display in the fragment title
    public final static String CATEGORY_NAME = "category_name";
    public final static String ITEM_NUM = "item_num";
    private RequestParams requestParams;
    private String url;
    public final static int TIMEOUT = 5*1000;

    public ParamsFactory(Bundle args){
        if(isSearch(args)){
            requestParams =  searchParams(args);
        }else if(isBrowseable(args)){
            requestParams = browseByCategory(args);
        }
    }
    RequestParams searchParams(Bundle args){
        url = Routes.SEARCH_URL;
        int page =  args.getInt(PAGE);
        String query =  args.getString(SEARCH);
        RequestParams q = new RequestParams();
        q.put(PAGE, page);
        q.put(SEARCH,query);
        return  q;
    }


    public  static  RequestParams searchParams(int page, String query){
        RequestParams q = new RequestParams();
        q.put(PAGE, page);
        q.put(SEARCH,query);
        return  q;
    }
    public void updatePage(int page, Bundle bundle){
        getRequestParams().put(PAGE,page);
        bundle.putInt(PAGE,page);
    }
    public  RequestParams browseByCategory(Bundle args){
        url = Routes.BROWSE_BY;
        String category =  args.getString(CATEGORY_ID);
        RequestParams q = new RequestParams();
        int page =  args.getInt(PAGE);
        q.put(PAGE, page);
        q.put(CATEGORY_ID,category);
        return  q;
    }
  public static RequestParams browseByCategory(int page, String category){
        String p =  String.valueOf(page);
        RequestParams q = new RequestParams();
        q.put(PAGE, p);
        q.put(CATEGORY_ID,category);
        return  q;
    }
    protected boolean isSearch(Bundle args){
      return   args.getString(SEARCH) != null;
    }
    protected boolean isBrowseable(Bundle args){
        return   args.getString(CATEGORY_ID) != null;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public static Bundle setBrowseable(String category, String category_name,int page, int item_num){
        Bundle b = new Bundle();
        b.putString(CATEGORY_ID, category);
        b.putString(CATEGORY_NAME, category_name);
        b.putInt(PAGE, page);
        b.putInt(ITEM_NUM, item_num);
        return  b;
    }
    public static Bundle setSearchable( String search_query, int page){
        Bundle b = new Bundle();
        b.putString(SEARCH, search_query);
        b.putInt(PAGE, page);
        return  b;
    }

    public String getUrl() {
        return url;
    }
}
