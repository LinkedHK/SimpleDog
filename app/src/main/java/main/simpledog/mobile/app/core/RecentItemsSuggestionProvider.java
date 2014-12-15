package main.simpledog.mobile.app.core;


import android.content.SearchRecentSuggestionsProvider;

public class RecentItemsSuggestionProvider extends SearchRecentSuggestionsProvider{

    public final static String AUTHORITY = "com.simpledog.RecentItemsSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

  public   RecentItemsSuggestionProvider(){
        setupSuggestions(AUTHORITY,MODE);

    }

}
