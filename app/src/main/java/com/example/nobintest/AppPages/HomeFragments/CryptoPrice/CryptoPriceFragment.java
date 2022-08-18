package com.example.nobintest.AppPages.HomeFragments.CryptoPrice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.nobintest.R;
import com.example.nobintest.graphActivity.GraphActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CryptoPriceFragment extends Fragment {

    ProgressBar progressBar;

    ArrayList<CryptoCoin> cryptoCoins;
    private CoinViewAdapter coinViewAdapter;
    private SwipeRefreshLayout swipeContainer;
    public static String TAG = "TAG";

    private NetworkRequestHandler mNetworkRequestHandler;

    private static final int GET_FIRST_PACK = 0;
    private static final int GET_MORE_COINS = 1;
    public static final int Done = 2;

    private AtomicBoolean networkRequestBusy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_cryptoprice, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        networkRequestBusy = new AtomicBoolean(false);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        cryptoCoins = new ArrayList<>();

        mNetworkRequestHandler = new NetworkRequestHandler(cryptoCoins, this);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // it has not a fixed Size.
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify item click listener.
        View.OnClickListener recyclerViewItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                String symbol = cryptoCoins.get(itemPosition).getSymbol();
                Intent i = new Intent(getActivity(), GraphActivity.class);
                i.putExtra("SYMBOL", symbol);
                startActivity(i);

            }
        };
        // specify an adapter (see also next example)

        coinViewAdapter = new CoinViewAdapter(cryptoCoins, mNetworkRequestHandler, recyclerViewItemClickListener);
        recyclerView.setAdapter(coinViewAdapter);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);


        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.

                if (networkRequestBusy.get()) {
                    return;
                }

                Log.v(TAG, "Refreshed");
                mNetworkRequestHandler.sendEmptyMessage(GET_FIRST_PACK);

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int visibleThreshold = 1;

                if (!networkRequestBusy.get() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    Log.v(CryptoPriceFragment.TAG, "Scrolled");
                    mNetworkRequestHandler.sendEmptyMessage(GET_MORE_COINS);
                }

            }
        });

        mNetworkRequestHandler.sendEmptyMessage(GET_FIRST_PACK);

        return view;
    }


    static class NetworkRequestHandler extends Handler {

        private WeakReference<CryptoPriceFragment> cryptoPriceFragmentWeakReference;
        private ArrayList<CryptoCoin> cryptoCoins;

        public NetworkRequestHandler(ArrayList<CryptoCoin> cryptoCoins, CryptoPriceFragment cryptoPriceFragment) {
            this.cryptoPriceFragmentWeakReference = new WeakReference<>(cryptoPriceFragment);
            this.cryptoCoins = cryptoCoins;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            Log.v(TAG, "handling Massage: mHandler");
            CryptoPriceFragment cryptoPriceFragment = cryptoPriceFragmentWeakReference.get();
            if (cryptoPriceFragment == null) {
                return;
            }


            if (msg.what == GET_FIRST_PACK) {

                cryptoPriceFragment.networkRequestBusy.set(true);
                cryptoPriceFragment.coinViewAdapter.loadSomeCoinsStartAt(1, 10);

            } else if (msg.what == GET_MORE_COINS) {

                cryptoPriceFragment.networkRequestBusy.set(true);
                if (cryptoCoins.size() == 0) {
                    cryptoPriceFragment.coinViewAdapter.loadSomeCoinsStartAt(1, 10);
                } else {
                    cryptoPriceFragment.coinViewAdapter.loadSomeCoinsStartAt(cryptoCoins.size(), 10);
                }

            } else if (msg.what == Done) {

                if (cryptoPriceFragment.progressBar.getVisibility() == ProgressBar.VISIBLE) {
                    cryptoPriceFragment.progressBar.setVisibility(ProgressBar.GONE);
                }
                cryptoPriceFragment.coinViewAdapter.addAll((ArrayList<CryptoCoin>) msg.obj);
                cryptoPriceFragment.swipeContainer.setRefreshing(false);
                cryptoPriceFragment.networkRequestBusy.set(false);
            }
        }

    }

}
