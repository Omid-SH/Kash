package com.example.nobintest.AppPages.UserProfile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;
import com.example.nobintest.nobitex.dataTypes.BankCard;

import java.util.ArrayList;


public class BankCardsAdapter extends RecyclerView.Adapter<BankCardsAdapter.ViewHolder> {

    ArrayList<BankCard> bankCards;

    public BankCardsAdapter(ArrayList<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_bank_card_template, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String bankName = "بانک " + bankCards.get(position).getBank();
        holder.bank.setText(bankName);
        holder.number.setText(bankCards.get(position).getNumber());
        holder.owner.setText(bankCards.get(position).getOwner());
    }

    @Override
    public int getItemCount() {
        return bankCards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bank, number, owner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bank = itemView.findViewById(R.id.bank_card_name);
            number = itemView.findViewById(R.id.bank_card_number);
            owner = itemView.findViewById(R.id.bank_card_owner);
        }
    }
}
