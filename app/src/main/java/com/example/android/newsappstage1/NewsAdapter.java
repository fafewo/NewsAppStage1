package com.example.android.newsappstage1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> news){
        super(context,0, news );
    }
    @Override
    public View getView (int i, View convertView, ViewGroup parent){
        //is the existing view reused?if not inflate
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from( getContext() ).inflate( R.layout.list_item, parent, false );
        }
        News currentArticle = getItem( i );
        //textView vor the Date
        TextView dateView = listItemView.findViewById( R.id.Date );
        String textDate = (currentArticle.getDate());
        dateView.setText( textDate );
        //find the TextView fpr the description
        TextView headlineView = listItemView.findViewById( R.id.textHeadline );
        String textHeadline =  (currentArticle.getTitle());
        headlineView.setText(textHeadline);
       //find the TextView for the article url itself
      //  TextView articleView = (TextView)listItemView.findViewById( R.id.thumbnail );
      //  String textArticle = (currentArticle.getUrl());
      //  articleView.setText(textArticle);

       //shows the section
        TextView sectionView = listItemView.findViewById( R.id.section );
        String textSection =(currentArticle.getSection());
        sectionView.setText( textSection );

        //shows the author
        TextView authorView = listItemView.findViewById( R.id.author );
        String textAuthor =(currentArticle.getAuthor());
        authorView.setText( textAuthor );

        return listItemView;
    }


}
