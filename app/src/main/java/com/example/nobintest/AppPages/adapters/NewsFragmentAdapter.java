package com.example.nobintest.AppPages.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nobintest.AppPages.NewsFragments.NewsTextFragment;
import com.example.nobintest.AppPages.NewsFragments.NewsVideosFragment;

public class NewsFragmentAdapter extends FragmentStatePagerAdapter {

    public NewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewsTextFragment();
            case 1:
                return new NewsVideosFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            //
            //Your tab titles
            //
            case 0:
                return "news";
            case 1:
                return "videos";
            default:
                return null;
        }
    }
}
