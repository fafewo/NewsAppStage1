package com.example.android.newsappstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
//import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = NewsActivity.class.getName();

    //URL for the Footballnews data from the guardian dataset
    private static final String GUARDIAN_REQUEST_URL ="https://content.guardianapis.com/sections?q=football&from-date=2018-07-18&api-key=11479b37-af7f-4d05-b340-0f8f9dc138ad";

    private static final int NEWS_LOADER_ID = 1;
    //TextView to display when the list is empty
    private TextView mEmptyStateTextView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news );
        //find a reference to the ListView in the layout
        ListView newsListView = (ListView)findViewById( R.id.list );
        //to create a new ArrayAdapter of the news
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        //set the adapter on the ListView so the list can be populated in the user interface
        newsListView.setAdapter( mAdapter );
        mEmptyStateTextView = (TextView)findViewById( R.id.empty_view );
        newsListView.setEmptyView( mEmptyStateTextView );
        newsListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Find the current article that was clicked
                News currentArticle = mAdapter.getItem(i);
                //Convert the String URL into a URI object( passing the intent constructor)
                Uri newsUri = Uri.parse( currentArticle.getUrl() );
                //new intent for viewing the article URI
                Intent websiteIntent = new Intent( Intent.ACTION_VIEW, newsUri );
                //send intent for launching a new activity
                startActivity( websiteIntent );
            }
        } );
        //Get a reference to the connectivityManager for checking network connectivity
        ConnectivityManager connManage = (ConnectivityManager)
                getSystemService( Context.CONNECTIVITY_SERVICE );
        //details about the currently active defaut network
        NetworkInfo networkInfo = connManage.getActiveNetworkInfo();

        //if network connection available, fetch data
        if (networkInfo != null && networkInfo.isConnected()){
            //interact with loaders:
            LoaderManager loaderManager = getLoaderManager();
            //initializing the loader
            loaderManager.initLoader( NEWS_LOADER_ID, null,this );
        }else {
            //Display error Otherwise
            View loadIndicator = findViewById( R.id.load_indicator);
            loadIndicator.setVisibility( View.GONE );

            //error message
            mEmptyStateTextView.setText( R.string.no_internet_connection );
        }
    }
    @Override
    public Loader<List<News>>onCreateLoader(int i, Bundle bundle){
        return new NewsLoader( this, GUARDIAN_REQUEST_URL );
    }
    public void onLoadFinished(Loader<List<News>>loader, List<News>newsList){
        //Data has been loaded therefore hide loading indicator
        View loadingIndicator = findViewById( R.id.load_indicator );
        loadingIndicator.setVisibility( View.GONE );
        //clear adapter of previous news
        mAdapter.clear();

        if(newsList != null && !newsList.isEmpty()){
            mAdapter.addAll( newsList );
        }
    }
    @Override
    public void onLoaderReset(Loader<List<News>>loader){
        mAdapter.clear();
    }
}
