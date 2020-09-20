package com.example.my2cents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username, password;

        username = findViewById(R.id.userEditText);
        password = findViewById(R.id.pwEditText);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class); //comment out these lines if enabling authentication
                startActivity(startIntent); //comment out these lines if enabling authentication

                /******************** Uncomment the following lines to enable username and password authentication in cooperation with "login" button **************************************************/
//                String correct_username = "admin";
//                String correct_password = "password";
//                //validate inputs
//                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "Insufficient data provided", Toast.LENGTH_LONG).show();
//                }
//                else if (username.getText().toString().equals(correct_username)) {
//                    //check password
//                    if (password.getText().toString().equals(correct_password)) {
//                        Toast.makeText(MainActivity.this, "Successful Login",Toast.LENGTH_LONG).show();
//
//                        //It will move to next screen after successful sign on
//                        Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
//                        startActivity(startIntent);
//                    }
//                    else {
//                        Toast.makeText(MainActivity.this, "Invalid username/password",Toast.LENGTH_LONG).show();
//                    }
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Invalid username/password", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
}