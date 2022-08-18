package com.example.nobintest.AppPages.NewsFragments;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.AppPages.NewsFragments.effectedPart.EffectedCoinsAdapter;
import com.example.nobintest.JsonDataTypes.Data;
import com.example.nobintest.JsonDataTypes.Packet;
import com.example.nobintest.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import io.cryptocontrol.cryptonewsapi.CryptoControlApi;
import io.cryptocontrol.cryptonewsapi.models.Article;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsTextFragment extends Fragment {

    public static int downLoadEffectedCoin = 0;
    private static int getNews = 1;
    private static int loadNewsIntoUI = 2;
    private static int extractDict = 3;

    private NewsRequestHandler newsRequestHandler;
    private NewsListAdapter newsListAdapter;
    private HashMap<String, Integer> slugToId;

    private ProgressBar progressBar;

    private static String TAG = "TAG";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_text, null);
        newsRequestHandler = new NewsRequestHandler(this);
        slugToId = new HashMap<>();
        progressBar = view.findViewById(R.id.news_progressBar);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_list_recyclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // it has not a fixed Size.
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // specify an adapter (see also next example)
        newsListAdapter = new NewsListAdapter(getActivity(), newsRequestHandler);
        recyclerView.setAdapter(newsListAdapter);
        getDict();

        return view;
    }


    private void getDict() {

        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder;
        urlBuilder = HttpUrl.parse("https://pro-api.coinmarketcap.com/v1/cryptocurrency/map")
            .newBuilder();

        String url = urlBuilder.build().toString();

        String API_KEY = "56fc3cea-8c6b-435e-8200-f81204e7c881";

        Request request = new Request.Builder().url(url)
            .addHeader("X-CMC_PRO_API_KEY", API_KEY)
            .addHeader("Accept", "application/json")
            .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Test", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {

                    Log.v("Test", "ErrorHappened During Getting Dict");

                } else {

                    Log.v("Test", "ResponseGot");
                    String body = response.body().string();

                    try {

                        Packet packet = new Gson().fromJson(body, Packet.class);
                        Log.v("Test", "Done");

                        Message message = new Message();
                        message.what = extractDict;
                        message.obj = packet;
                        newsRequestHandler.sendMessage(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private void unVisible(ImageView imageView) {
        imageView.setVisibility(View.GONE);
    }

    public static class NewsRequestHandler extends Handler {

        private WeakReference<NewsTextFragment> newsTextFragmentWeakReference;

        public NewsRequestHandler(NewsTextFragment newsTextFragment) {
            this.newsTextFragmentWeakReference = new WeakReference<>(newsTextFragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            final NewsTextFragment newsTextFragment = newsTextFragmentWeakReference.get();
            if (newsTextFragment == null) {
                return;
            }

            if (msg.what == downLoadEffectedCoin) {

                final EffectedCoinsAdapter.Data data = (EffectedCoinsAdapter.Data) msg.obj;

                if (newsTextFragment.slugToId.get(data.getSlug()) == null) {
                    newsTextFragment.unVisible(data.getImageView());
                } else {
                    String id = String.valueOf(newsTextFragment.slugToId.get(data.getSlug()));
                    String url = "https://s2.coinmarketcap.com/static/img/coins/64x64/".concat(id).concat(".png");
                    newsTextFragment.loadImage(url, data.getImageView());
                }

            } else if (msg.what == getNews) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        newsTextFragment.getNews();
                    }
                });
                thread.start();

            } else if (msg.what == loadNewsIntoUI) {

                newsTextFragment.newsListAdapter.addNews((List<Article>) msg.obj);
                newsTextFragment.progressBar.setVisibility(ProgressBar.GONE);

            } else if (msg.what == extractDict) {
                Packet packet = (Packet) msg.obj;
                newsTextFragment.fillDict(packet.getData());
            }
        }

    }

    private void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url)
            .into(imageView);
    }

    private void fillDict(List<Data> dataList) {
        for (Data data : dataList) {
            slugToId.put(data.getSlug(), data.getId());
            Log.i("dict", data.getSlug() + data.getId());
        }
        newsRequestHandler.sendEmptyMessage(getNews);
    }

    /*
    // I refused to use this function to getImage URL because of 2 main reasons
    // 1.It causes a lot of Network Traffic.
    // 2.We don't have a Premium account so server doesn't give us
    // the amount of BandWidth that we need.

    private void getCoinImageURL(final String slug, final ImageView imageView) {

        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder;
        urlBuilder = HttpUrl.parse("https://pro-api.coinmarketcap.com/v1/cryptocurrency/info")
                .newBuilder();
        urlBuilder.addQueryParameter("slug", slug);
        String url = urlBuilder.build().toString();

        String API_KEY = "6856beeb-daff-4e78-8125-84c7f6119c56";

        Request request = new Request.Builder().url(url)
                .addHeader("X-CMC_PRO_API_KEY", API_KEY)
                .addHeader("Accept", "application/json")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Test", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);

                } else {

                    Log.v("Test", "ResponseGot");
                    String body = response.body().string();

                    try {

                        JSONObject jsonObject = new JSONObject(body);
                        Log.v("Test", body);
                        String data = jsonObject.getJSONObject("data").toString();
                        String id = data.substring(data.indexOf("\"") + 1, data.indexOf(":") - 1);
                        Log.v("Test", id);

                        String url = "https://s2.coinmarketcap.com/static/img/coins/64x64/".concat(id).concat(".png");

                        Message message = new Message();
                        message.obj = new CoinImage(url, imageView);
                        message.what = loadIntoImageView;
                        newsRequestHandler.sendMessage(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    */

    private void getNews() {

        // Connect to the CryptoControl API
        String API_KEY = "dcb47064894e8502ba385338f2860116";
        CryptoControlApi api = new CryptoControlApi(API_KEY);

        // Connect to a self-hosted proxy server (to improve performance) that points to cryptoControl.io
        CryptoControlApi proxyApi = new CryptoControlApi(API_KEY, "http://cryptocontrol_proxy/api/v1/public");

        // Enable sentiment datapoints
        //api.enableSentiment();

        // Get top crypto news
        api.getTopNews(new CryptoControlApi.OnResponseHandler<List<Article>>() {

            public void onSuccess(List<Article> body) {

                Message message = new Message();
                message.what = loadNewsIntoUI;
                message.obj = body;
                newsRequestHandler.sendMessage(message);

            }

            public void onFailure(Exception e) {
                e.printStackTrace();
            }

        });

        /*

        // Get latest tweets for bitcoin
        api.getLatestTweetsByCoin("bitcoin", new CryptoControlApi.OnResponseHandler<List<Tweet>>() {
            @Override
            public void onSuccess(List<Tweet> body) {
                for (Tweet post : body) {
                    Log.v(TAG, post.getId());
                }
            }


            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        // Get latest russian crypto news
        api.getLatestNews(Language.RUSSIAN, new CryptoControlApi.OnResponseHandler<List<Article>>() {
            @Override
            public void onSuccess(List<Article> body) {
                for (Article article : body) {
                    Log.v(TAG, article.getTitle());
                }
            }


            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        // Get rich metadata (wallets, blockexplorers, twitter handles etc..) for ethereum
        api.getCoinDetails("ethereum", new CryptoControlApi.OnResponseHandler<CoinDetail>() {
            @Override
            public void onSuccess(CoinDetail body) {
                Log.v(TAG, body.getDescription());
            }


            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        */

    }

}
