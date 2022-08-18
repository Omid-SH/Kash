package com.example.nobintest.AppPages.HomeFragments.CryptoPrice;


import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;
import com.example.nobintest.timeUtils.NumberRounderUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoinViewAdapter extends RecyclerView.Adapter<CoinViewHolder> {

    private ArrayList<CryptoCoin> cryptoCoins;
    private CryptoPriceFragment.NetworkRequestHandler handler;
    private View.OnClickListener itemClickListener;

    public CoinViewAdapter(ArrayList<CryptoCoin> cryptoCoins, CryptoPriceFragment.NetworkRequestHandler handler, View.OnClickListener itemClickListener) {
        this.cryptoCoins = cryptoCoins;
        this.handler = handler;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.coin_layout, parent, false);

        view.setOnClickListener(itemClickListener);
        return new CoinViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {

        CryptoCoin item = cryptoCoins.get(position);

        holder.coinName.setText(item.getName());
        holder.coinSymbol.setText(item.getSymbol());

        long price = Math.round(Double.parseDouble(item.getPrice_usd()));
        holder.coinPrice.setText(String.valueOf(price));

        // rounding numbers.

        /*
        long oneHourPercent = Math.round(Double.parseDouble(item.getPercentChange1h()));
        long oneDayPercent = Math.round(Double.parseDouble(item.getPercentChange24h()));
        long oneWeekPercent = Math.round(Double.parseDouble(item.getPercentChange7d()));
        */

        double oneHourPercent = NumberRounderUtil.correctRounding(item.getPercentChange1h());
        double oneDayPercent = NumberRounderUtil.correctRounding(item.getPercentChange24h());
        double oneWeekPercent = NumberRounderUtil.correctRounding(item.getPercentChange7d());


        holder.oneHourPercent.setText(String.valueOf(oneHourPercent).concat("%"));
        holder.oneDayPercent.setText(String.valueOf(oneDayPercent).concat("%"));
        holder.sevenDaysPercent.setText(String.valueOf(oneWeekPercent).concat("%"));

        //Loading Image
        String url = String.format("https://s2.coinmarketcap.com/static/img/coins/64x64/%s.png", item.getId());
        Picasso.get().load(url)
            .into(holder.getCoinImage());

        holder.oneHourPercent.setTextColor(item.getPercentChange1h().contains("-") ?
            Color.RED : Color.GREEN);

        holder.oneDayPercent.setTextColor(item.getPercentChange24h().contains("-") ?
            Color.RED : Color.GREEN);

        holder.sevenDaysPercent.setTextColor(item.getPercentChange7d().contains("-") ?
            Color.RED : Color.GREEN);

    }

    @Override
    public int getItemCount() {
        return cryptoCoins.size();
    }


    public void clear() {
        cryptoCoins.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<CryptoCoin> list) {

        for (CryptoCoin cryptoCoin : list) {
            if (!cryptoCoins.contains(cryptoCoin)) {
                cryptoCoins.add(cryptoCoin);
            }
        }

        notifyDataSetChanged();
    }

    public void loadSomeCoinsStartAt(int index, int numOfGonnaLoadedCoins) {

        Log.v(CryptoPriceFragment.TAG, "start Requesting");
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl.Builder urlBuilder;
        urlBuilder = HttpUrl.parse("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
            .newBuilder();

        // index >= 1
        urlBuilder.addQueryParameter("start", String.valueOf(index));
        urlBuilder.addQueryParameter("limit", String.valueOf(numOfGonnaLoadedCoins));
        urlBuilder.addQueryParameter("convert", "USD");
        String url = urlBuilder.build().toString();

        String API_KEY = "6856beeb-daff-4e78-8125-84c7f6119c56";

        Request request = new Request.Builder().url(url)
            .addHeader("X-CMC_PRO_API_KEY", API_KEY)
            .addHeader("Accept", "application/json")
            .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v(CryptoPriceFragment.TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {

                    Log.v(CryptoPriceFragment.TAG, "ResponseGot");
                    String body = response.body().string();

                    try {

                        final ArrayList<CryptoCoin> models = new ArrayList<>();

                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            compileJsonToModel(models, json);
                            //Log.v(MainActivity.TAG, json.toString());
                        }

                        /*for (CryptoCoin model : models) {
                            Log.v(MainActivity.TAG, model.toString());
                        }*/

                        Message message = new Message();
                        message.what = CryptoPriceFragment.Done;
                        message.obj = models;
                        handler.sendMessage(message);
                        handler.sendMessage(message);

                    } catch (Exception e) {
                        Log.v(CryptoPriceFragment.TAG, e.getMessage());
                    }

                    //Log.v(MainActivity.TAG, body);
                }

            }
        });
    }

    public void compileJsonToModel(List<CryptoCoin> models, JSONObject jsonObject) {


        try {


            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String symbol = jsonObject.getString("symbol");
            JSONObject usdObject = jsonObject.getJSONObject("quote").getJSONObject("USD");

            String price = usdObject.getString("price");
            String oneHourChange = usdObject.getString("percent_change_1h");
            String oneDayChange = usdObject.getString("percent_change_24h");
            String oneWeekChange = usdObject.getString("percent_change_7d");

            models.add(new CryptoCoin(id, name, symbol, price,
                oneHourChange,
                oneDayChange,
                oneWeekChange));

        } catch (JSONException e) {
            Log.v(CryptoPriceFragment.TAG, "JSON part: ErrorHappened");
            e.printStackTrace();
        }

    }

}