package com.pstreets.navigator.search;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider{
	public final static String AUTHORITY = "com.pstreets.navigator.search.SearchSuggestionProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;
	public SearchSuggestionProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
}
