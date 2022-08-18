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
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class SellOrdersAdapter extends RecyclerView.Adapter<SellOrdersAdapter.ViewHolder> {

    private ArrayList<Pair<String, String>> list;

    public SellOrdersAdapter(Map<String, String> bids) {
        list = new ArrayList<>();
        for (String string : bids.keySet()) {
            list.add(new Pair<String, String>(string, bids.get(string)));
        }
        Collections.sort(list, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                return o1.first.compareTo(o2.first);
            }
        });
    }

    @NonNull
    @Override
    public SellOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_sell_order, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellOrdersAdapter.ViewHolder holder, int position) {
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
            price = itemView.findViewById(R.id.sell_price);
            amount = itemView.findViewById(R.id.sell_amount);
        }
    }
}
