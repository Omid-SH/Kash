package com.example.nobintest.AppPages.ToolsFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.R;
import com.example.nobintest.graphActivity.GraphActivity;

public class ChartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools_chart, container, false);
        LottieAnimationView selectButton = (LottieAnimationView) view.findViewById(R.id.select_chart_coin);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataManager appDataManager = AppDataManager.getInstance(getActivity());
                final String[] strings = appDataManager.getChartCoinList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick a coin");
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedMarket = strings[which];
                        Intent i = new Intent(getActivity(), GraphActivity.class);
                        i.putExtra("SYMBOL", selectedMarket);
                        startActivity(i);
                    }
                });
                builder.show();
            }
        });
        return view;
    }
}
