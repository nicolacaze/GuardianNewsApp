package com.example.android.guardiannewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;

    private static final String GUARDIAN_API_REQUEST = "http://content.guardianapis.com/search?" +
            "section=culture|film&api-key=test&show-tags=contributor";

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private NewsAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView newsListView = (ListView) findViewById(R.id.list);

        //Identify our widget.
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(mAdapter);

        //When refresh is called we update the list.
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsListView.setAdapter(mAdapter);
                //Dismiss loading icon when done
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //On user click, one article opens the relative webpage from guardian website.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News currentNews = mAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent webCall = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(webCall);
            }
        });

        //Before launching loading manager, we check if Internet connection is active.
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //Set the empty view when no data are to be displayed.
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        //Check if user's Internet connection is functioning.
        if (isConnected) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
            spinner.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, GUARDIAN_API_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.GONE);

        //Set empty state text to display "No data found."
        mEmptyStateTextView.setText(R.string.empty_view);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
