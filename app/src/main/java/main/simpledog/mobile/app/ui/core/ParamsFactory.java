package main.simpledog.mobile.app.ui.core;

import android.os.Bundle;
import com.loopj.android.http.RequestParams;
import main.simpledog.mobile.app.rest.Routes;

public class ParamsFactory {

    private RequestParams requestParams;
    private String url;
    public final static int TIMEOUT = 5*1000;

    public ParamsFactory(Bundle args){
        if(args != null){
            buildFactory(args);
        }
    }
    protected void buildFactory(Bundle args){
        ParamsFactoryParser factoryParser = new ParamsFactoryParser();
        if(factoryParser.isSearch(args)){
            requestParams =  searchParams(args);
        }else if(factoryParser.isBrowseable(args)){
            requestParams = browseByCategory(args);
        }else if(factoryParser.isListCategories(args)){
            requestParams = categoriesList(args);
        }
    }

    RequestParams searchParams(Bundle args){
        url = Routes.SEARCH_URL;
        int page =  args.getInt(ParamsFactoryConstants.PAGE);
        String query =  args.getString(ParamsFactoryConstants.SEARCH);
        RequestParams q = new RequestParams();
        q.put(ParamsFactoryConstants.PAGE, page);
        q.put(ParamsFactoryConstants.SEARCH,query);
        return  q;
    }


    public  static  RequestParams searchParams(int page, String query){
        RequestParams q = new RequestParams();
        q.put(ParamsFactoryConstants.PAGE, page);
        q.put(ParamsFactoryConstants.SEARCH,query);
        return  q;
    }
    public void updatePage(int page, Bundle bundle){
        getRequestParams().put(ParamsFactoryConstants.PAGE,page);
        bundle.putInt(ParamsFactoryConstants.PAGE,page);
    }
    public RequestParams categoriesList(Bundle args){
        return  null;
    }
    public  RequestParams browseByCategory(Bundle args){
        url = Routes.BROWSE_BY;
        String category =  args.getString(ParamsFactoryConstants.CATEGORY_ID);
        RequestParams q = new RequestParams();
        int page =  args.getInt(ParamsFactoryConstants.PAGE);
        q.put(ParamsFactoryConstants.PAGE, page);
        q.put(ParamsFactoryConstants.CATEGORY_ID,category);
        return  q;
    }
  public static RequestParams browseByCategory(int page, String category){
        String p =  String.valueOf(page);
        RequestParams q = new RequestParams();
        q.put(ParamsFactoryConstants.PAGE, p);
        q.put(ParamsFactoryConstants.CATEGORY_ID,category);
        return  q;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public static Bundle setBrowseable(String category, String category_name,int page, int item_num){
        Bundle b = new Bundle();
        b.putString(ParamsFactoryConstants.CATEGORY_ID, category);
        b.putString(ParamsFactoryConstants.CATEGORY_NAME, category_name);
        b.putInt(ParamsFactoryConstants.PAGE, page);
        b.putInt(ParamsFactoryConstants.ITEM_NUM, item_num);
        return  b;
    }

    public   static Bundle setListCategories(){
        Bundle bundle = new Bundle();
        String keyv = ParamsFactoryConstants.CATEGORY_LIST;
        bundle.putString(keyv,keyv);
        return  bundle;
    }


    public String getUrl() {
        return url;
    }
}
