package com.example.nobintest.AppPages.UserProfile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.R;
import com.example.nobintest.nobitex.dataTypes.BankAccount;

import java.util.ArrayList;

public class BankAccountsAdapter extends RecyclerView.Adapter<BankAccountsAdapter.ViewHolder> {

    ArrayList<BankAccount> bankAccounts;

    public BankAccountsAdapter(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @NonNull
    @Override
    public BankAccountsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.single_account_template, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAccountsAdapter.ViewHolder holder, int position) {
        String bankName = "بانک " + bankAccounts.get(position).getBank();
        holder.bank.setText(bankName);
        holder.number.setText(bankAccounts.get(position).getNumber());
        holder.owner.setText(bankAccounts.get(position).getOwner());
        holder.shaba.setText(bankAccounts.get(position).getShaba());
    }

    @Override
    public int getItemCount() {
        return bankAccounts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bank, number, owner, shaba;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bank = itemView.findViewById(R.id.bank_account_name);
            number = itemView.findViewById(R.id.bank_account_number);
            owner = itemView.findViewById(R.id.bank_account_owner);
            shaba = itemView.findViewById(R.id.bank_account_shaba);
        }
    }
}
