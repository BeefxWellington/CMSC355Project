package com.example.my2cents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private final String CHANNEL_ID = "bills";
    public final int NOTIFICATIONS_ID = 001;

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

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                        //set icon in the status bar for
                        builder.setSmallIcon(R.drawable.ic_money);
                        //set title of notification
                        builder.setContentTitle("My2Cents Notification");
                        //dismiss notification on tap
                        builder.setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(NOTIFICATIONS_ID, builder.build());

                Intent startIntent = new Intent(getApplicationContext(), recycler.class); //comment out these lines if enabling authentication
                startActivity(startIntent); //comment out these lines if enabling authentication

                /******************** Uncomment the following lines to enable username and password authentication in cooperation with "login" button **************************************************/
//                String correct_username = "admin";
//                String correct_password = "password";SA
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