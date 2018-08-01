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
        super(context,0, (List<News>) news );
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        //is the existing view reused?if not inflate
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from( getContext() ).inflate( R.layout.list_item, parent, false );
        }
        String headline = getContext().getString( R.string.headline );

        //find the TextView fpr the description
        TextView headlineView = (TextView) listItemView.findViewById( R.id.textHeadline );
        headlineView.setText(headline);
        return listItemView;
    }

}
