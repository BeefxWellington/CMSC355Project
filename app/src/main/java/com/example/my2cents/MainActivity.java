package com.example.my2cents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView Register;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //User = (EditText)findViewById(R.id.userEditText);
        //Pass = (EditText)findViewById(R.id.pwEditText);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Register = (TextView)findViewById(R.id.fpwTextView);
        firebaseAuth = FirebaseAuth.getInstance();




        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.pwEditText);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate(email.getText().toString(), password.getText() .toString());


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

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });





    }
    public void validate(String email, String password){

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (MainActivity.this, SecondActivity.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
