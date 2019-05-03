package com.example.mybudget.Account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybudget.R;

import java.util.Collections;
import java.util.List;

public class AccountsRecyclerViewAdapter extends RecyclerView.Adapter<AccountsViewHolder>{
    List<AccountsRow> list = Collections.emptyList();
    Context context;

    public AccountsRecyclerViewAdapter(List<AccountsRow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //Inflate the layout, initialize the View Holder
    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.accounts_row_layout, parent, false);
        AccountsViewHolder holder = new AccountsViewHolder(v);
        return holder;
    }

    //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
    @Override
    public void onBindViewHolder(AccountsViewHolder holder, int position) {
        int type = list.get(position).status;
        if(type == 0 || type == 2){
            holder.title.setText(list.get(position).title);
            holder.title.setTextColor(context.getColor(R.color.red));
            holder.date.setText(""+list.get(position).date);
            holder.date.setTextColor(context.getColor(R.color.red));
            holder.amount.setText(""+list.get(position).amount + " SEK");
            holder.amount.setTextColor(context.getColor(R.color.red));
        }
        else if(type == 1 || type == 3){
            holder.title.setText(list.get(position).title);
            holder.title.setTextColor(context.getColor(R.color.green));
            holder.date.setText(""+list.get(position).date);
            holder.date.setTextColor(context.getColor(R.color.green));
            holder.amount.setText(""+list.get(position).amount + " SEK");
            holder.amount.setTextColor(context.getColor(R.color.green));
        }
        int id= list.get(position).id;
        holder.itemView.setTag(id);
    }

    //returns the number of elements the RecyclerView will display
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, AccountsRow row) {
        list.add(position, row);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(AccountsRow row) {
        int position = list.indexOf(row);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
