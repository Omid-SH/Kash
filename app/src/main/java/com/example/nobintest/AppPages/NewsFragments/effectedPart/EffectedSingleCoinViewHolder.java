package com.example.nobintest.AppPages.NewsFragments.effectedPart;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;

public class EffectedSingleCoinViewHolder extends RecyclerView.ViewHolder {


    private ImageView coin;

    public EffectedSingleCoinViewHolder(View itemView) {

        super(itemView);
        coin = itemView.findViewById(R.id.effected_coin);

    }

    public ImageView getCoin() {
        return coin;
    }
}
