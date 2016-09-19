package com.example.android.guardiannewsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nicolaslacaze on 10/09/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    //Define public constructor
    public NewsAdapter(Activity context, List<News> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);
        }

        News currentNews = getItem(position);

        TextView sectionName = (TextView) listItemView.findViewById(R.id.section_title);
        sectionName.setText(currentNews.getSectionName());

        TextView publicationDate = (TextView) listItemView.findViewById(R.id.publication_date);

        //Get the current article raw publication date.
        String rawPublicationDate = currentNews.getPublicationDate();

        //Format it to keep only the date.
        String formattedPublicationDate = formatDate(rawPublicationDate);

        //Set formatted date to correct TextView
        publicationDate.setText(formattedPublicationDate);

        TextView url = (TextView) listItemView.findViewById(R.id.article_title);
        url.setText(currentNews.getTitle());

        return listItemView;
    }

    private String formatDate(String rawDate) {
        int charIndex = rawDate.indexOf("T");
        return rawDate.substring(0, charIndex);
    }
}
