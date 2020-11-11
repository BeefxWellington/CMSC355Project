package com.example.my2cents;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView day = itemView.findViewById(R.id.quantity_item);
    TextView nameItem = itemView.findViewById(R.id.name_item);
    TextView timeStampItem = itemView.findViewById(R.id.timestamp_item);
    TextView balanceChange = itemView.findViewById(R.id.balance_change);
    TextView balanceSubText = itemView.findViewById(R.id.balance_subtext);

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
