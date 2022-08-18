package com.example.nobintest.AppPages.NewsFragments;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nobintest.R;

public class SingleNewsViewHolder extends RecyclerView.ViewHolder {


    private TextView newsSource, title, description, link;
    private RecyclerView effectedCoins;
    private SeekBar seekBar;

    public SingleNewsViewHolder(final View itemView) {
        super(itemView);

        newsSource = itemView.findViewById(R.id.news_source);
        title = itemView.findViewById(R.id.news_title);
        description = itemView.findViewById(R.id.news_description);
        link = itemView.findViewById(R.id.news_link);

        seekBar = itemView.findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // nothing
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();

                if (progress > 50) {
                    LottieAnimationView like = itemView.findViewById(R.id.like);
                    like.playAnimation();
                } else if (progress < 50) {
                    LottieAnimationView dislike = itemView.findViewById(R.id.dislike);
                    dislike.playAnimation();
                }
            }

        });

        effectedCoins = itemView.findViewById(R.id.effected_coin_recyclerview);

    }


    public TextView getNewsSource() {
        return newsSource;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getLink() {
        return link;
    }

    public RecyclerView getEffectedCoins() {
        return effectedCoins;
    }
}
