package com.example.nobintest.AppPages.TradeFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

public class TradeBuyFragment extends Fragment {
    private String market;
    private AppDataManager appDataManager;
    private ProgressBar progressBarSell, progressBarBuy;
    private RecyclerView recyclerViewSell, recyclerViewBuy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trade_buy, null);

        progressBarSell = view.findViewById(R.id.progress_bar_sell);
        progressBarBuy = view.findViewById(R.id.progress_bar_buy);

        recyclerViewBuy = view.findViewById(R.id.buy_list_recycler);
        recyclerViewSell = view.findViewById(R.id.sell_list_recycler);

        appDataManager = AppDataManager.getInstance(getContext());
        market = appDataManager.getBuyMarket();

        Button button = view.findViewById(R.id.buy_btn);
        TextView header = view.findViewById(R.id.market);

        header.setText(market);
        String[] tokens = market.split("[ ]+");
        getGlobalOrders(tokens[0] + tokens[2]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView currencyTextView = view.findViewById(R.id.market);
                    EditText priceEditText = view.findViewById(R.id.buy_price);
                    EditText amountEditText = view.findViewById(R.id.buy_amount);
                    String currency = currencyTextView.getText().toString();
                    String[] tokens = currency.split("[ ]+");

                    if (tokens[2].equals("IRT")) {
                        tokens[2] = "rls";
                    }

                    DataManager dataManager = DataManager.getInstance();
                    AppDataManager appDataManager = AppDataManager.getInstance(getActivity());

                    if (!appDataManager.getProfileState().equals(AppDataManager.USER_STATE_SIGNED_IN)) {
                        Toast.makeText(getActivity(), "FIRST SIGN IN", Toast.LENGTH_SHORT).show();
                    } else if (dataManager.isNetworkConnected(getActivity())) {
                        DataManager.BuyPageHandler buyPageHandler = DataManager.getBuyPageHandlerInstance();
                        buyPageHandler.setTradeBuyFragmentWeakReference(new WeakReference<TradeBuyFragment>(TradeBuyFragment.this));
                        dataManager.addNewOrder("buy", "limit", tokens[0].toLowerCase(), tokens[2].toLowerCase(),
                            amountEditText.getText().toString(), priceEditText.getText().toString());

                        priceEditText.setText("");
                        amountEditText.setText("");
                    } else {
                        Toast.makeText(getActivity(), "NO INTERNET CONNECTION!", Toast.LENGTH_SHORT).show();
                    }

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    View view = getActivity().getCurrentFocus();
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view == null) {
                        view = new View(getActivity());
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView header = v.findViewById(R.id.market);
                final ArrayList<String> arrayList = appDataManager.getAllMarketList();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final String[] strings = arrayList.toArray(new String[0]);
                builder.setTitle("Pick a market");
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedMarket = arrayList.get(which);
                        appDataManager.setBuyMarket(selectedMarket);
                        header.setText(selectedMarket);
                        String[] tokens = selectedMarket.split("[ ]+");
                        getGlobalOrders(tokens[0] + tokens[2]);
                    }
                });
                builder.show();
            }
        });

        return view;
    }

    private void getGlobalOrders(String symbol) {
        DataManager dataManager = DataManager.getInstance();
        if (dataManager.isNetworkConnected(getActivity())) {
            DataManager.BuyPageHandler buyPageHandler = DataManager.getBuyPageHandlerInstance();
            buyPageHandler.setTradeBuyFragmentWeakReference(new WeakReference<TradeBuyFragment>(TradeBuyFragment.this));

            progressBarBuy.setVisibility(View.VISIBLE);
            progressBarSell.setVisibility(View.VISIBLE);
            dataManager.getGlobalOrderBook(symbol, "BuyPage");
            Toast.makeText(getContext(), "GetGlobalOrder " + symbol, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "CONNECT TO INTERNET " + symbol, Toast.LENGTH_SHORT).show();
            progressBarBuy.setVisibility(View.GONE);
            progressBarSell.setVisibility(View.GONE);
        }
    }

    public void setSellsRecycler(Map<String, String> bids) {
        try {
            recyclerViewSell.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewSell.setAdapter(new SellOrdersAdapter(bids));
            progressBarSell.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBuysRecycler(Map<String, String> asks) {
        try {
            recyclerViewBuy.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewBuy.setAdapter(new BuyOrdersAdapter(asks));
            progressBarBuy.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
