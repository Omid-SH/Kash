package com.example.nobintest.AppPages.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nobintest.AppPages.HomeFragments.CryptoPrice.CryptoPriceFragment;
import com.example.nobintest.AppPages.HomeFragments.NobitexCryptoPriceFragment;

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    public HomeFragmentAdapter(FragmentManager fm) {
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
                return new CryptoPriceFragment();
            case 1:
                return new NobitexCryptoPriceFragment();
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
                return "crypto price";
            case 1:
                return "nobitex market";
            default:
                return null;
        }
    }
}