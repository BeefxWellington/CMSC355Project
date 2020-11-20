package com.example.my2cents;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class intro extends AppCompatActivity {

    private IntroAdapter introAdapter;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        setupIntroItems();

        ViewPager2 introViewPager = findViewById(R.id.intro_viewpager);
        introViewPager.setAdapter(introAdapter);

        handler = new Handler();
        // this tells the program how long to run the splash screen
        public void run() {
            Intent intent=new Intent(intro.this,SecondActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private  void setupIntroItems() {

        List<IntroItem> introItems = new ArrayList<>();

        IntroItem welcomeScreen = new IntroItem();
        welcomeScreen.setTitle("Welcome to My2Cents!");
        welcomeScreen.setDesc("Hi there! I am Clawdia, your app guide and new financial advisor!");
        welcomeScreen.setImage(R.drawable.intro_money);

        IntroItem homeScreen = new IntroItem();
        homeScreen.setTitle("Account Overview");
        homeScreen.setDesc("Your Home Page will show account overview information for you. You can swipe left or right to view different quick breakdowns.");
        homeScreen.setImage(R.drawable.intro_money);

        IntroItem addScreen = new IntroItem();
        addScreen.setTitle("Let Me Know When Something Changes");
        addScreen.setDesc("Click the plus button on the home screen to update your balance with expenses or income.");
        addScreen.setImage(R.drawable.intro_add);

        IntroItem analyticsScreen = new IntroItem();
        analyticsScreen.setTitle("Dive Deeper");
        analyticsScreen.setDesc("Click on the details page to show different income and outcome graphs as well as the history of your account.");
        analyticsScreen.setImage(R.drawable.intro_analytics);

        IntroItem settingsScreen = new IntroItem();
        settingsScreen.setTitle("Set Up Notifications");
        settingsScreen.setDesc("et up notifications for upcoming expenses in the settings page. Have fun and remember, you are not alone in this.");
        settingsScreen.setImage(R.drawable.intro_settings);

        introItems.add(welcomeScreen);
        introItems.add(homeScreen);
        introItems.add(addScreen);
        introItems.add(analyticsScreen);
        introItems.add(settingsScreen);

        introAdapter = new IntroAdapter(introItems);

    }
}