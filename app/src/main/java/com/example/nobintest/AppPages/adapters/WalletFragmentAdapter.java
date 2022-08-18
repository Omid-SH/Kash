package com.example.nobintest.AppPages.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nobintest.AppPages.WalletFragments.WalletFragment;

public class WalletFragmentAdapter extends FragmentStatePagerAdapter {

    public WalletFragmentAdapter(FragmentManager fm) {
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
                return new WalletFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            //
            //Your tab titles
            //
            case 0:
                return "Wallet";
            default:
                return null;
        }
    }
}
