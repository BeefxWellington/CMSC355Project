package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class UsernameSettings extends Fragment {

    EditText username;
    EditText password;
    Button changeUsername;


    public UsernameSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_username_settings, container, false);
        changeUsername = (Button) view.findViewById(R.id.changeusername);
        username = (EditText) view.findViewById(R.id.usernameEditText);
        password = (EditText) view.findViewById(R.id.passwordEditText);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a new username.", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your password to confirm changes.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Successfully changed username!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }
}