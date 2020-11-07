package com.example.my2cents;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    @Override
    // open this screen on create every time for 2.75 seconds
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final int SPLASH_SCREEN_DISPLAY_TIME = 2750;

        // the below schedules the splash appearance
        handler = new Handler();
        // this tells the program how long to run the splash screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_DISPLAY_TIME); // 2.75 seconds

    }
}