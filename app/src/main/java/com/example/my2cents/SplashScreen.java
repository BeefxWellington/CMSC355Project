package com.example.my2cents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    @Override
    // open this screen on create every time for 2.75 seconds
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
        },2750); // 2.75 seconds

    }
}