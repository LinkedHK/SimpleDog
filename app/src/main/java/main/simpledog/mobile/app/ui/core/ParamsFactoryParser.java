package main.simpledog.mobile.app.ui.core;


import android.os.Bundle;

public class ParamsFactoryParser {

    public static Bundle setSearchable( String search_query, int page){
        Bundle b = new Bundle();
        b.putString(ParamsFactoryConstants.SEARCH, search_query);
        b.putInt(ParamsFactoryConstants.PAGE, page);
        return  b;
    }
    public boolean isSearch(Bundle args){
        return   args.getString(ParamsFactoryConstants.SEARCH) != null;
    }
    public boolean isBrowseable(Bundle args){
        return   args.getString(ParamsFactoryConstants.CATEGORY_ID) != null;
    }

    public boolean isListCategories(  Bundle args ){
        return  args.getString(ParamsFactoryConstants.CATEGORY_LIST) != null;
    }

}
