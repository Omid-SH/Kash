package com.example.nobintest.AppPages.HomeFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.R;

import java.lang.ref.WeakReference;

public class NobitexCryptoPriceFragment extends Fragment {

    private String srcCurrency = "btc";
    private String dstCurrency = "rls";
    private TextView srcCurrencyEditText;
    private TextView dstCurrencyEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_nobitex_cryptocurrency, container, false);

        srcCurrencyEditText = view.findViewById(R.id.src_currency);
        srcCurrencyEditText.setText(srcCurrency.toUpperCase());
        srcCurrencyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataManager appDataManager = AppDataManager.getInstance(getActivity());
                final String[] strings = appDataManager.getNobitexMarketCoinList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick a coin");
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        srcCurrency = strings[which];
                        srcCurrencyEditText.setText(srcCurrency.toUpperCase());
                    }
                });
                builder.show();
            }
        });

        dstCurrencyEditText = view.findViewById(R.id.dst_currency);
        dstCurrencyEditText.setText(dstCurrency.toUpperCase());
        dstCurrencyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataManager appDataManager = AppDataManager.getInstance(getActivity());
                final String[] strings = appDataManager.getNobitexMarketCoinList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick a coin");
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dstCurrency = strings[which];
                        dstCurrencyEditText.setText(dstCurrency.toUpperCase());
                    }
                });
                builder.show();
            }
        });

        LottieAnimationView buttonCheck = (LottieAnimationView) view.findViewById(R.id.check_btn);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager dataManager = DataManager.getInstance();
                if (dataManager.isNetworkConnected(getActivity())) {
                    DataManager.NobitexMarketPriceHandler nobitexMarketPriceHandler = DataManager.getNobitexMarketPriceHandlerInstance();
                    nobitexMarketPriceHandler.setNobitexCryptoPriceFragmentWeakReference(new WeakReference<NobitexCryptoPriceFragment>(NobitexCryptoPriceFragment.this));
                    dataManager.getNobitexMarket(srcCurrency, dstCurrency);
                } else {
                    Toast.makeText(getActivity(), "NO INTERNET!", Toast.LENGTH_SHORT);
                }

            }
        });
        return view;
    }

}
