package com.example.android.guardiannewsapp;

/**
 * Created by nicolaslacaze on 10/09/16.
 */
public class News {

    private String mSectionName;

    private String mTitle;

    private String mPublicationDate;

    private String mUrl;

    /**Define the class constructor below
     *
     * @param section
     * @param title
     * @param date
     * @param url
     */
    public News(String section, String title, String date, String url) {
        mSectionName = section;
        mTitle = title;
        mPublicationDate = date;
        mUrl = url;
    }

    //Define getter methods to access each data.

    public String getSectionName() {
        return mSectionName;
    }

    public String getTitle() { return mTitle;}

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return mSectionName + " " + mTitle + " " + mPublicationDate + " " + mUrl;
    }
}
