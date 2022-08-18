package com.example.nobintest.AppPages.WalletFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.R;

import java.lang.ref.WeakReference;

public class WalletFragment extends Fragment {
    private RecyclerView walletRecycler;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        walletRecycler = view.findViewById(R.id.wallet_recycler);
        progressBar = view.findViewById(R.id.progress_bar_wallet);

        DataManager dataManager = DataManager.getInstance();
        AppDataManager appDataManager = AppDataManager.getInstance(getActivity());

        if (!appDataManager.getProfileState().equals(AppDataManager.USER_STATE_SIGNED_IN)) {
            Toast.makeText(getActivity(), "Sign in First!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        } else if (!dataManager.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else {
            DataManager.WalletHandler walletHandler = DataManager.getWalletHandlerInstance();
            walletHandler.setWalletFragmentWeakReference(new WeakReference<WalletFragment>(this));
            dataManager.getWallets();
        }
        return view;
    }

    public void setRecyclerView(String[] walletCoins, String[] balance, String[] rialBalance) {
        if (walletCoins.length != balance.length || balance.length != rialBalance.length) {
            return;
        }

        try {
            walletRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            walletRecycler.setAdapter(new WalletAdapter(walletCoins, balance, rialBalance));
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
