package com.example.android.guardiannewsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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

        ViewHolder holder;

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);

            holder = new ViewHolder();
            holder.sectionTitle = (TextView) listItemView.findViewById(R.id.section_title);
            holder.publicationDate = (TextView) listItemView.findViewById(R.id.publication_date);
            holder.articleTitle = (TextView) listItemView.findViewById(R.id.article_title);
            holder.contributor = (TextView) listItemView.findViewById(R.id.contributor);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        News currentNews = getItem(position);

        holder.sectionTitle.setText(currentNews.getSectionName());
        //Get the current article raw publication date.
        String rawPublicationDate = currentNews.getPublicationDate();
        //Format it to keep only the date.
        String formattedPublicationDate = formatDate(rawPublicationDate);
        //Set formatted date to correct TextView.
        holder.publicationDate.setText(formattedPublicationDate);
        //Set the current article title.
        holder.articleTitle.setText(currentNews.getTitle());

        String contributors = currentNews.getContributors();
        //Handle case when no contributors are available.
        if (contributors == null) {
            //Make TextView invisible if nothing is to be displayed.
            holder.contributor.setVisibility(View.GONE);
        } else {
            holder.contributor.setText(contributors);
        }
        return listItemView;
    }

    private String formatDate(String rawDate) {
        int charIndex = rawDate.indexOf("T");
        return rawDate.substring(0, charIndex);
    }

    private static class ViewHolder {
        private TextView sectionTitle;
        private TextView publicationDate;
        private TextView articleTitle;
        private TextView contributor;
    }
}
