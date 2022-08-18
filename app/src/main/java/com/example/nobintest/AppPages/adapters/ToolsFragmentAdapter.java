package com.example.nobintest.AppPages.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nobintest.AppPages.ToolsFragments.ChartFragment;
import com.example.nobintest.AppPages.ToolsFragments.PriceAlertFragment;
import com.example.nobintest.AppPages.ToolsFragments.TradeBotFragment;

public class ToolsFragmentAdapter extends FragmentStatePagerAdapter {

    public ToolsFragmentAdapter(FragmentManager fm) {
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
                return new ChartFragment();
            case 1:
                return new PriceAlertFragment();
            case 2:
                return new TradeBotFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            //
            //Your tab titles
            //
            case 0:
                return "chart";
            case 1:
                return "price alert";
            case 2:
                return "trade bot";
            default:
                return null;
        }
    }
}
