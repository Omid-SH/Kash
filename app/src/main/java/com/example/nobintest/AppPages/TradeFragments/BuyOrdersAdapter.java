package com.example.nobintest.AppPages.TradeFragments;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class BuyOrdersAdapter extends RecyclerView.Adapter<BuyOrdersAdapter.ViewHolder> {

    private ArrayList<Pair<String, String>> list;

    public BuyOrdersAdapter(Map<String, String> asks) {
        list = new ArrayList<>();
        for (String string : asks.keySet()) {
            list.add(new Pair<String, String>(string, asks.get(string)));
        }
        Collections.sort(list, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                return o2.first.compareTo(o1.first);
            }
        });
    }

    @NonNull
    @Override
    public BuyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_buy_order, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyOrdersAdapter.ViewHolder holder, int position) {
        holder.price.setText(list.get(position).first);
        holder.amount.setText(list.get(position).second);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView price, amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.buy_price);
            amount = itemView.findViewById(R.id.buy_amount);
        }
    }
}
