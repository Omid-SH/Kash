package com.example.nobintest.AppPages.HomeFragments.CryptoPrice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;

public class CoinViewHolder extends RecyclerView.ViewHolder {


    private ImageView coinImage;
    public TextView coinSymbol, coinName, coinPrice, oneHourPercent, oneDayPercent, sevenDaysPercent;

    public CoinViewHolder(View itemView) {

        super(itemView);
        coinImage = itemView.findViewById(R.id.coin_icon);
        coinSymbol = itemView.findViewById(R.id.coin_symbol);
        coinName = itemView.findViewById(R.id.coin_name);
        coinPrice = itemView.findViewById(R.id.priceUsdText);

        oneHourPercent = itemView.findViewById(R.id.percentHourChange);
        oneDayPercent = itemView.findViewById(R.id.percent1DayChange);
        sevenDaysPercent = itemView.findViewById(R.id.percent7DayChange);

    }

    public ImageView getCoinImage() {
        return coinImage;
    }

}
