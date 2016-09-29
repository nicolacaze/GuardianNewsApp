package com.example.android.guardiannewsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolaslacaze on 10/09/16.
 */
public final class Utils {

    //Log tag defined below for future Log to console.
    private static final String LOG_TAG = Utils.class.getName();


    public static List<News> fetchingNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while making the HTTP request", e);
        }

        return extractDataFromJson(jsonResponse);
    }

    //Create a properly formatted URL based on a protocol string.
    private static URL createUrl(String requestUrl) {
        URL url;

        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "The url provided is malformed", e);
            return null;
        }
        return url;
    }

    //Open a HTTP connection with Guardianapis server
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        int httpResponseCode;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            httpResponseCode = urlConnection.getResponseCode();

            if (httpResponseCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.v(LOG_TAG, "HTTP response code is " + httpResponseCode);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making the HTTP request", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<News> extractDataFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<News> data = new ArrayList<News>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject fullResponse = baseJsonResponse.getJSONObject("response");
            JSONArray results = fullResponse.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject article = results.getJSONObject(i);
                String section = article.getString("sectionName");
                String title = article.getString("webTitle");
                String date = article.getString("webPublicationDate");
                String url = article.getString("webUrl");

                //Declare our builder to create a String of contributors' name.
                StringBuilder builder = new StringBuilder();
                String contributors;

                /*Check if contributors are available and get results. Otherwise return null for
                contributors.
                 */
                JSONArray tags = article.getJSONArray("tags");
                if (tags.length() != 0) {
                    for (int y = 0; y < tags.length(); y++) {
                        JSONObject contributor = tags.getJSONObject(y);
                        String contributorName = contributor.getString("webTitle");
                        //Add each contributor to our builder.
                        builder.append(contributorName);
                    }
                    //Transform our builder into a String argument for our News object.
                    contributors = builder.toString();
                    News news = new News(section, title, date, contributors, url);
                    data.add(news);
                    //Clear builder for the next loop.
                    builder.setLength(0);
                } else {
                    News news = new News(section, title, date, null, url);
                    data.add(news);
                }
            }
            return data;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        return null;
    }
}
