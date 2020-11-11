package com.example.my2cents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Onboarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dots;
    SlideAdaption slideAdaption;
    TextView[] slide_dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        //hooks
        viewPager = findViewById(R.id.slider);
        dots = findViewById(R.id.dots);

        slideAdaption = new SlideAdaption(this);
        viewPager.setAdapter(slideAdaption);
        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);
    }

    private void addDots(int position) {

        slide_dots = new TextView[4];
        dots.removeAllViews();

        for ( int i = 0; i < slide_dots.length; i++){
            slide_dots[i] = new TextView(this);
            slide_dots[i].setText(Html.fromHtml("&#8226;"));
            slide_dots[i].setTextSize(35);

            dots.addView(slide_dots[i]);
        }

        if(slide_dots.length > 0){
            slide_dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            ;
        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            ;
        }
    }


}