package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private String url = "https://content.guardianapis.com/search?api-key=test&q=business";
    private NewsAdapter adapter;
    private ListView listView;
    private TextView textView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        listView = rootView.findViewById(R.id.list);
        textView = rootView.findViewById(R.id.text);
        progressBar = rootView.findViewById(R.id.prgressbar);

        //To check network connection is there or not.
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (!isConnected) {
            textView.setText(R.string.no_internet_connection);
            progressBar.setVisibility(View.GONE);
        } else
            getLoaderManager().initLoader(0, null, this);

        adapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);
                String currentURL = currentNews.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentURL));
                startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
//        progressBar.setVisibility(View.VISIBLE);
//        textView.setVisibility(View.GONE);
        return new NewsLoader(getActivity(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
//        progressBar.setVisibility(View.GONE);
        adapter.clear();
        if (data != null && !data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}