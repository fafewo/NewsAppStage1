package com.example.android.newsappstage1;

import org.json.JSONObject;

public class News {

    //title of the guardian section shown in the APP
    private String mTitle;

    //the URL of the API
    private String mWeburl;

    //new Word object

    /**
     * @param title shows the section/article of the guardian
     * @param weburl is the website
     */
    public News(String title, String weburl ){
        mTitle = title;
        mWeburl = weburl;
    }
    //returns the article
    public String getTitle (){return mTitle;}
    //returns the URL to find the article
    public String getUrl (){return mWeburl;}
}
