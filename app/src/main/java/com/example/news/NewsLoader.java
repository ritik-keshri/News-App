package com.example.news;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private final String url;

    public NewsLoader(FragmentActivity activity, String url) {
        super(activity);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        return QueryUtils.fetchNewsData(url);
    }
}
