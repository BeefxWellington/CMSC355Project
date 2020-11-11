package com.example.my2cents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdaption extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdaption(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.ic_money,
            R.drawable.ic_home,
            R.drawable.ic_add,
            R.drawable.ic_analytics,
            R.drawable.ic_settings
    };

    int headings[] ={
            R.string.first_title,
            R.string.second_title,
            R.string.third_slide,
            R.string.fourth_slide,
            R.string.fifth_slide
    };

    int text[] ={
            R.string.first_text,
            R.string.second_text,
            R.string.third_text,
            R.string.fourth_text,
            R.string.fifth_text
    };


    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slides,container,false);

        // hooks
        ImageView imageView = view.findViewById(R.id.onb_image);
        TextView title = view.findViewById(R.id.slide_title);
        TextView desc = view.findViewById(R.id.slide_text);

        imageView.setImageResource(images[position]);
        title.setText(headings[position]);
        desc.setText(text[position]);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);    }
}
