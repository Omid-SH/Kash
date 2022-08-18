package com.example.nobintest.AppPages.WalletFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;


public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private String[] currency, balance, rialBalance;

    public WalletAdapter(String[] currency, String[] balance, String[] rialBalance) {
        this.currency = currency;
        this.balance = balance;
        this.rialBalance = rialBalance;
    }

    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_wallet_template, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {
        holder.currency.setText(currency[position].toUpperCase());
        holder.balance.setText(balance[position]);
        holder.rialBalance.setText(rialBalance[position]);
    }

    @Override
    public int getItemCount() {
        return currency.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView currency, balance, rialBalance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currency = itemView.findViewById(R.id.coin_name);
            balance = itemView.findViewById(R.id.coin_active_balance);
            rialBalance = itemView.findViewById(R.id.coin_rial_balance);
        }
    }
}
