package com.example.news;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    private Context context;

    public FragmentPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new WorldFragment();
        else if (position == 1)
            return new SportsFragment();
        else
            return new BusinessFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return context.getString(R.string.category_world);
        else if (position == 1)
            return context.getString(R.string.category_sports);
        else
            return context.getString(R.string.category_busisness);
    }
}