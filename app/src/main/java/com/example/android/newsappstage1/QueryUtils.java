package com.example.android.newsappstage1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.example.android.newsappstage1.NewsActivity.LOG_TAG;

    //Helper methods to request and receive data from Guardian

public final class QueryUtils{


    private QueryUtils(){}
        //returning a list of (@link News) objects that has been built up from parsing a JSON response

   public static List<News> extractFeatureFromJson(String newsJSON) {
       //if JSON string is empty or null, return early.
       if (TextUtils.isEmpty( newsJSON )) {
           return null;
       }
       //creating an Arrylist to put News in
       List<News> newsList =  new ArrayList<>();
       //Try parsing SAMPLE_JSON_RESPONSE, when problems occur with the JSON format, JSONException object will be thrown
       try {
           //build a list of article objects from the JSON response
           JSONObject baseJsonResponse = new JSONObject( newsJSON );
           //extract the JSONObject associated with the key called  "response"
           JSONObject newsArray = baseJsonResponse.getJSONObject(  "response"  );
          // Extract JSONArray with the name results
           JSONArray results = newsArray.getJSONArray( "results" );
           //create an {@link News) object for each article in the news Array


           for (int i = 0; i< newsArray.length(); i++){
               //get an article at position i within the list of articles

               JSONObject currentArticle = results.getJSONObject( i) ;
               // extract the JSON object for the latest news
               //extract the following values from the array "results"

               //Date
               String date = currentArticle.getString( "webPublicationDate" );
               //webUrl:
               String weburl = currentArticle.getString( "webUrl" );
               //webTitle
               String title = currentArticle.getString ( "webTitle"  );
               String section = currentArticle.optString( "sectionName" );
                //get the authors name of each article
                String author = "";
               JSONArray tags = currentArticle.getJSONArray( "tags" );
               for (int pos =0 ; pos < tags.length(); pos ++) {
                   JSONObject currentTag = tags.getJSONObject( pos );
                   author = currentTag.optString( "webTitle" );
                   break;
               }
               //creating a new {@link News) object with the weburl and web title,...,
               //from the JSON response
               News news = new News(date, title, weburl,  section, author);
               newsList.add(news);
           }
       }
       catch (JSONException e){
           //If an error occurs when a statement above is executed
           //catch exception here to not crash the app. Print a log message with the message from the exception
           Log.e("QueryUtils","Problem in parsing the JSON results of the news", e);
       }
       //returning a list of news from the section Football of the guardian newspaper
       return newsList;
   }
   //returning new URL object
    private static URL createUrl (String stringUrl){
        URL url = null;
        try {
            url = new URL( stringUrl );
        }catch (MalformedURLException e){
            Log.e( LOG_TAG, "URL not build",e );
        }
        return url;
    }
   //Make a HTTP request to the given URL to return a String as response
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse ="";
        //when URL is null, early return.
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //if there is the case of a successful request (response code 200),
            //then read the in put stream and parse the response.
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error in this response code: " + urlConnection.getResponseCode());
            }
        }catch ( IOException e){
            Log.e(LOG_TAG, "Problem to retrieve the results for the footballnews", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    // convert the {@Link inputStream} into a string which contains the whole JSON response from the server
    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, Charset.forName( "UTF-8" ) );
            BufferedReader reader = new BufferedReader( inputStreamReader );
            String line = reader.readLine();
            while (line != null){
                output.append( line );
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    //Query the guardian dataset and return a list of {@Link News} objects.
    public static List<News> fetchNewsData(String requestUrl){
        //creating URL object
        URL url = createUrl(requestUrl);
        //to perform the HTTP request to the URLto receive a JSON response back
        String jsonResponse =null;
        try {
            jsonResponse = makeHttpRequest( url );
        }catch (IOException e){
            Log.e(LOG_TAG, "There is a problem in making the HTTP request.",e);
        }
        //Extracting the fields from the JSON response to create a list of {@link News}
        List<News> news = extractFeatureFromJson( jsonResponse );
        //return the list
        return news;
    }


}

