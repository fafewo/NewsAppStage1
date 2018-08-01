package com.example.android.newsappstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    //tag for log messages
    private static final String LOG_TAG = NewsLoader.class.getName();

    //Query URL
    private String mUrl;

    /**
     * to construct a new {@link NewsLoader}
     * @param context
     * @param url
     */

    public NewsLoader (Context context, String url){
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    //It's on a background thread
    @Override
    public List<News>loadInBackground(){
        if (mUrl == null) {
            return null;
        }
        //extract list of footballnews after performing the network request and parsing the response
        List<News> newsList = QueryUtils.fetchNewsData( mUrl );
        return newsList;
    }
}
