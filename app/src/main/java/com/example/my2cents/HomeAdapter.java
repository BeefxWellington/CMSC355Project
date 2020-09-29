package com.example.my2cents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class HomeAdapter extends PagerAdapter {

    private List<HomeModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public HomeAdapter(List<HomeModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_cardview, container, false);

        TextView title, firstTitle, secondTitle, thirdTitle, firstAmount, secondAmount, thirdAmount, firstDate, secondDate, thirdDate;

        title = view.findViewById(R.id.title);
        firstTitle = view.findViewById(R.id.firstTitle);
        firstAmount = view.findViewById(R.id.firstAmount);
        firstDate = view.findViewById(R.id.firstDate);
        secondTitle = view.findViewById(R.id.secondTitle);
        secondAmount = view.findViewById(R.id.secondAmount);
        secondDate = view.findViewById(R.id.secondDate);
        thirdTitle = view.findViewById(R.id.thirdTitle);
        thirdAmount = view.findViewById(R.id.thirdAmount);
        thirdDate = view.findViewById(R.id.thirdDate);

        title.setText(models.get(position).getTitle());
        firstTitle.setText(models.get(position).getFirstTitle());
        firstAmount.setText(models.get(position).getFirstAmount());
        firstDate.setText(models.get(position).getFirstDate());
        secondTitle.setText(models.get(position).getSecondTitle());
        secondAmount.setText(models.get(position).getSecondAmount());
        secondDate.setText(models.get(position).getSecondDate());
        thirdTitle.setText(models.get(position).getThirdTitle());
        thirdAmount.setText(models.get(position).getThirdAmount());
        thirdDate.setText(models.get(position).getThirdDate());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
