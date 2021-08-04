package com.example.news;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(FragmentActivity activity, ArrayList<News> news) {
        super(activity, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        News currentNews = (News) getItem(position);

        TextView sectionName = listItemView.findViewById(R.id.sectionName);
        sectionName.setText(currentNews.getSectionName());

        TextView title = listItemView.findViewById(R.id.title);
        title.setText(currentNews.getTittle());

        TextView date = listItemView.findViewById(R.id.date);
        date.setText(currentNews.getDate());

        return listItemView;
    }
}