package com.example.mybudget.Account;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mybudget.R;

public class AccountsViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    TextView date;
    TextView title;
    TextView amount;
    TextView status;

    AccountsViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        date = (TextView) itemView.findViewById(R.id.date);
        amount = (TextView) itemView.findViewById(R.id.amount);
        status = (TextView) itemView.findViewById(R.id.status);
    }
}
