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

    private static final String GUARDIAN_API_REQUEST = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":27233,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2724,\"orderBy\":\"newest\",\"results\":[{\"id\":\"culture/2016/sep/10/star-trek-is-a-hope-for-a-better-future-fans-on-the-50th-anniversary\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-10T06:00:24Z\",\"webTitle\":\"'Star Trek is a hope for a better future': fans on the 50th anniversary\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/10/star-trek-is-a-hope-for-a-better-future-fans-on-the-50th-anniversary\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/10/star-trek-is-a-hope-for-a-better-future-fans-on-the-50th-anniversary\",\"isHosted\":false},{\"id\":\"culture/2016/sep/09/five-of-the-best-theatre-shows-this-week\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-09T12:00:02Z\",\"webTitle\":\"Five of the best... theatre shows this week\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/09/five-of-the-best-theatre-shows-this-week\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/09/five-of-the-best-theatre-shows-this-week\",\"isHosted\":false},{\"id\":\"culture/2016/sep/09/three-of-the-best-dance-performances-this-week\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-09T12:00:01Z\",\"webTitle\":\"Three of the best... dance performances this week\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/09/three-of-the-best-dance-performances-this-week\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/09/three-of-the-best-dance-performances-this-week\",\"isHosted\":false},{\"id\":\"culture/2016/sep/09/five-of-the-best-classical-concerts\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-09T12:00:01Z\",\"webTitle\":\"Five of the best... classical concerts\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/09/five-of-the-best-classical-concerts\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/09/five-of-the-best-classical-concerts\",\"isHosted\":false},{\"id\":\"culture/2016/sep/09/five-of-the-best-films-out-now-in-the-uk\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-09T12:00:01Z\",\"webTitle\":\"Five of the best... films out now in the UK\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/09/five-of-the-best-films-out-now-in-the-uk\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/09/five-of-the-best-films-out-now-in-the-uk\",\"isHosted\":false},{\"id\":\"culture/2016/sep/09/five-of-the-best-rock-pop-gigs\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-09T12:00:01Z\",\"webTitle\":\"Five of the best... rock & pop gigs\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/09/five-of-the-best-rock-pop-gigs\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/09/five-of-the-best-rock-pop-gigs\",\"isHosted\":false},{\"id\":\"culture/2016/sep/09/the-10-best-things-to-do-this-week\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-09T08:00:57Z\",\"webTitle\":\"The 10 best… things to do this week\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/09/the-10-best-things-to-do-this-week\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/09/the-10-best-things-to-do-this-week\",\"isHosted\":false},{\"id\":\"culture/2016/sep/08/are-you-a-star-trek-fan-share-your-stories-and-photos\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-08T13:44:27Z\",\"webTitle\":\"Are you a Star Trek fan? Share your stories and photos\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/08/are-you-a-star-trek-fan-share-your-stories-and-photos\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/08/are-you-a-star-trek-fan-share-your-stories-and-photos\",\"isHosted\":false},{\"id\":\"culture/2016/sep/08/star-trek-helped-me-cope-with-the-death-of-my-sister\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-08T10:00:18Z\",\"webTitle\":\"How Star Trek helped me cope with the death of my sister\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/08/star-trek-helped-me-cope-with-the-death-of-my-sister\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/08/star-trek-helped-me-cope-with-the-death-of-my-sister\",\"isHosted\":false},{\"id\":\"culture/2016/sep/06/carrie-fisher-advice-column-cheating-husband-prostitutes\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2016-09-06T10:00:56Z\",\"webTitle\":\"Ask Carrie Fisher: my husband has been seeing prostitutes. Can I trust him again?\",\"webUrl\":\"https://www.theguardian.com/culture/2016/sep/06/carrie-fisher-advice-column-cheating-husband-prostitutes\",\"apiUrl\":\"https://content.guardianapis.com/culture/2016/sep/06/carrie-fisher-advice-column-cheating-husband-prostitutes\",\"isHosted\":false}]}}";

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
        return  url;
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

                News news = new News(section, title, date, url);
                data.add(news);
            }

            return data;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        return null;
    }
}
