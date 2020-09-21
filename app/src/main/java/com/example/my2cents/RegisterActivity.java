package com.example.my2cents;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText createUsername;
    private EditText createPassword;
    private EditText createEmail;
    private Button confirm;
    private TextView goBack;

    // add firebase here and imports on top
    // need to add goBack listener and activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupIds();

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