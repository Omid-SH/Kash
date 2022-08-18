package com.example.nobintest.AppPages.NewsFragments.effectedPart;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.AppPages.NewsFragments.NewsTextFragment;
import com.example.nobintest.R;

import java.util.ArrayList;

import io.cryptocontrol.cryptonewsapi.models.Article;

public class EffectedCoinsAdapter extends RecyclerView.Adapter<EffectedSingleCoinViewHolder> {


    ArrayList<Article.Coin> coins;
    NewsTextFragment.NewsRequestHandler handler;

    public EffectedCoinsAdapter(NewsTextFragment.NewsRequestHandler handler) {
        this.coins = new ArrayList<>();
        this.handler = handler;
    }

    @NonNull
    @Override
    public EffectedSingleCoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_effected_coin, parent, false);
        return new EffectedSingleCoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EffectedSingleCoinViewHolder holder, int position) {

        Article.Coin coin = coins.get(position);

        Data data = new Data(coin.getSlug(), holder.getCoin());
        Message message = new Message();
        message.what = NewsTextFragment.downLoadEffectedCoin;
        message.obj = data;
        handler.sendMessage(message);

    }

    public static class Data {

        private String slug;
        private ImageView imageView;

        public Data(String slug, ImageView imageView) {
            this.slug = slug;
            this.imageView = imageView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public String getSlug() {
            return slug;
        }
    }


    @Override
    public int getItemCount() {
        return coins.size();
    }

    public void addCoins(ArrayList<Article.Coin> coins) {
        this.coins.addAll(coins);
        notifyDataSetChanged();
    }

}
