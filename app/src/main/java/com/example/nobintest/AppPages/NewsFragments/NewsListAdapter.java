package com.example.nobintest.AppPages.NewsFragments;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.nobintest.R;
import com.example.nobintest.AppPages.NewsFragments.effectedPart.EffectedCoinsAdapter;
import com.example.nobintest.timeUtils.TimeDiffLabel;
import com.example.nobintest.timeUtils.TimeDifference;
import com.example.nobintest.timeUtils.UtilTimeDifference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.cryptocontrol.cryptonewsapi.models.Article;

public class NewsListAdapter extends RecyclerView.Adapter<SingleNewsViewHolder> {


    private Context context;
    private ArrayList<Article> news;
    private NewsTextFragment.NewsRequestHandler handler;

    public NewsListAdapter(Context context, NewsTextFragment.NewsRequestHandler handler) {
        this.news = new ArrayList<>();
        this.context = context;
        this.handler = handler;
    }

    public void addNews(List<Article> articles) {
        this.news.addAll(articles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Log.v("TAG","inside news List Adapter");

        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_news_layout, parent, false);
        return new SingleNewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SingleNewsViewHolder holder, int position) {

        //Log.v("TAG","binding newsViewHolder");

        Article thisNews = news.get(position);

        String publishTime = thisNews.getPublishedAt();
        String timeC = getCurrentDate().concat(".000Z");
        // TimeDiffLabel timeDiffLabel = new TimeDifference().calculateTimeDiff(timeC, publishTime);
        String releaseTime = UtilTimeDifference.calculateTimeDiff(timeC, publishTime); //String.valueOf(timeDiffLabel.getNumber()).concat(" ").concat(timeDiffLabel.getTimeScale());

        /*
        Log.v("TAG", timeC);
        Log.v("TAG", publishTime);
        Log.v("TAG", releaseTime);
        Log.v("TAG", UtilTimeDifference.calculateTimeDiff(timeC, publishTime));
        */

        // UtilTimeDifference.calculateTimeDiff(timeC, timeP)


        String newsTime = thisNews.getSource()
            .getName()
            .concat(" ")
            .concat(releaseTime);


        holder.getNewsSource().setText(newsTime);

        holder.getTitle().setText(thisNews.getTitle());
        holder.getDescription().setText(thisNews.getDescription());

        TextView link = holder.getLink();

        String newsLink = thisNews.getUrl();
        String news = link.getText().toString();
        String str_text = "<a href=" + newsLink + ">" + news + "</a>";

        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(str_text));

        RecyclerView recyclerView = holder.getEffectedCoins();

        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,
            false);

        recyclerView.setLayoutManager(layoutManager);


        ArrayList<Article.Coin> coins = (ArrayList<Article.Coin>) thisNews.getCoins();

        EffectedCoinsAdapter effectedCoinsAdapter = new EffectedCoinsAdapter(handler);
        recyclerView.setAdapter(effectedCoinsAdapter);
        effectedCoinsAdapter.addCoins(coins);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    private String getCurrentDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String result = simpleDateFormat.format(date);
        result = result.substring(0, result.indexOf(" ")).concat("T").concat(result.substring(result.indexOf(" ") + 1));
        return result;

    }


}
