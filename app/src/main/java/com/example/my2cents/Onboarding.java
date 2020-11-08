package com.example.my2cents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Onboarding extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
    }

// on the skip button, go to home
    public void Skipped(View view) {
        startActivity(new Intent(this,
                Home.class));
        finish();
    }

 // the next button should move to the next tutorial screen
    public void next(View view) {
    }
}