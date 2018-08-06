package com.example.android.newsappstage1;

import org.json.JSONObject;

public class News {

    //title of the guardian section shown in the APP
    private String mTitle;
    private String mDate;
    private String mWeburl;
    private String mSection;
    private String mAuthor;

    //new Word object

    /**
     * @param date
     * @param title
     * @param weburl
     * @param section
     * @param author
     */
    public News(String date, String title, String weburl, String section, String author ){
        mDate =date;
        mTitle = title;
        mWeburl = weburl;
        mSection = section;
        mAuthor = author;

    }
    //returns the article
    public String getTitle (){return mTitle;}
    //returns the URL to find the article
    public String getUrl (){return mWeburl;}
    public String getDate(){return mDate;}
    public String getSection() {return mSection;}
    public String getAuthor (){return  mAuthor;}
}
