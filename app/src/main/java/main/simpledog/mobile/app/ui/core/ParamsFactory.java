package main.simpledog.mobile.app.ui.core;

import android.os.Bundle;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.rest.Routes;

public class ParamsFactory {

    public final static String SEARCH = "search";
    public final static String PAGE = "page";
    public final static String CATEGORY = "category";
    public final static String CATEGORY_ID = "category_id";
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
    public ParamsFactory(RequestParams params){
            if(params.has(SEARCH)){

            }

    }



    RequestParams searchParams(Bundle args){
        url = Routes.SEARCH_URL;
        String page =  args.getString(PAGE);
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




    public  RequestParams browseByCategory(Bundle args){
        url = Routes.BROWSE_BY;
        String page =  args.getString(PAGE);
        String category =  args.getString(CATEGORY);
        RequestParams q = new RequestParams();
        q.put(PAGE, page);
        q.put(CATEGORY,category);
        return  q;
    }



  public static RequestParams browseByCategory(int page, String category){
        String p =  String.valueOf(page);
        RequestParams q = new RequestParams();
        q.put(PAGE, p);
        q.put(CATEGORY,category);
        return  q;
    }
    protected boolean isSearch(Bundle args){
      return   args.getString(SEARCH) != null;
    }
    protected boolean isBrowseable(Bundle args){
        return   args.getString(CATEGORY) != null;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public static Bundle setBrowseable(String category, int page){
        Bundle b = new Bundle();
        b.putString(CATEGORY, category);
        b.putInt(PAGE, page);
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
