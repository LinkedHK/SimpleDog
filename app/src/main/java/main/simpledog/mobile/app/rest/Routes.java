package main.simpledog.mobile.app.rest;


public class Routes {

    private static final String BASE_URL = "http://dis.ddns.net:3000/api";

    public static final String  SEARCH_URL = "/live_search";

    public static final String  BROWSE_BY = "/browse_by";

    public static final String  CATEGORIES_LIST = "/list_cat";

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
