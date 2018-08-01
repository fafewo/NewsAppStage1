package com.example.android.newsappstage1;

public class News {

    //title of the guardian section shown in the APP
    private String mFootball;

    //the URL of the API
    private String mWeburl;

    //new Word object

    /**
     * @param football shows the section/article of the guardian
     * @param weburl is the website
     */
    public News(String football, String weburl ){
        mFootball = football;
        mWeburl = weburl;
    }
    //returns the article
    public String getFootball (){return mFootball;}
    //returns the URL to find the article
    public String getUrl (){return mWeburl;}
}
