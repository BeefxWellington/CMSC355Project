package com.example.my2cents;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity implements Notifications.FragToAct, Home.ActToFrag {

    private FirebaseAuth  firebaseAuth;

    private boolean b1;
    private boolean b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // get the current user from database
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }


    @Override
    public void passSwitchState(boolean b1, boolean b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public boolean getIncomeSwitchState () {
        return b1;
    }

    @Override
    public boolean getExpenseSwitchState () {
        return b2;
    }


}

