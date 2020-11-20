package com.example.my2cents;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class LogListAdapter extends ArrayAdapter<Log> {

    Context context;
    int resource;

    public LogListAdapter(Context context, int resource, List<Log> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String month = getItem(position).getMonth();
        String day = getItem(position).getDay();
        String title = getItem(position).getTitle();
        String category = getItem(position).getCategory();
        String type = getItem(position).getType();
        String amount = getItem(position).getAmount();
        String balance = getItem(position).getBalance();

        Log log = new Log(month, day, title, category, type, amount, balance);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvMonth = (TextView) convertView.findViewById(R.id.logMonth);
        TextView tvDay = (TextView) convertView.findViewById(R.id.logDay);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.logTitle);
        TextView tvType = (TextView) convertView.findViewById(R.id.logType);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.logAmount);
        TextView tvBalance = (TextView) convertView.findViewById(R.id.logBalance);

        if (category.equals("Expense")) {
            tvAmount.setTextColor(Color.parseColor("#ff0000")); //set color to red
        }
        else {
            tvAmount.setTextColor(Color.parseColor("#47d147")); //set color to green
        }

        tvMonth.setText(month);
        tvDay.setText(day);
        tvTitle.setText(category);
        tvType.setText(type);
        tvAmount.setText(amount);
        tvBalance.setText("Balance: " + balance);

        return convertView;
    }
}
