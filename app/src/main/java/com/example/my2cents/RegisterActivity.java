package com.example.my2cents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private EditText createUsername;
    private EditText createPassword;
    private EditText createEmail;
    private Button confirm;
    private TextView goBack;
    private FirebaseAuth firebaseAuth;

    // add firebase here and imports on top
    // need to add goBack listener and activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupIds();

        firebaseAuth = FirebaseAuth.getInstance();
        final String names = createUsername.getText().toString().trim();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // data to database
                if(checkboxes()){
                    String email = createEmail.getText().toString().trim();
                    String pass = createPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser firebaseusername = firebaseAuth.getCurrentUser();
                                String ID = firebaseusername.getEmail();
                                String nodotemail = ID.replace(".",",");
                                FirebaseDatabase.getInstance().getReference("USERS").child(nodotemail).child("Details").setValue(names).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                                // need to fix database for users
                                Toast.makeText(RegisterActivity.this, "One moment", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });


        }

    private void setupIds() {
        createUsername = (EditText) findViewById(R.id.etName);
        createPassword = (EditText) findViewById(R.id.etPassword);
        createEmail = (EditText) findViewById(R.id.etEmail);
        confirm = (Button) findViewById(R.id.btnRegister);
        goBack = (TextView) findViewById(R.id.tvGoback);
    }
    private Boolean checkboxes(){
        Boolean emptyBox = false;

        String nameBox = createUsername.getText().toString();
        String passBox  = createPassword.getText().toString();
        String emailBox = createEmail.getText().toString();


        if (nameBox.isEmpty() && passBox.isEmpty() && emailBox.isEmpty()) {
            Toast.makeText(this, "Please enter your information", Toast.LENGTH_SHORT).show();


        }
        else {
            emptyBox = true;

        }
        return emptyBox;
    }
}