package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class PasswordSettings extends Fragment {

    EditText newPassword;
    EditText currentPassword;
    EditText confirmPassword;

    public PasswordSettings() {
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
        View view = inflater.inflate(R.layout.fragment_password_settings, container, false);
        Button b1 = (Button) view.findViewById(R.id.changepassword);
        newPassword = (EditText) view.findViewById(R.id.newPasswordEditText);
        currentPassword = (EditText) view.findViewById(R.id.currentPasswordEditText);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPasswordEditText);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a new password.", Toast.LENGTH_SHORT).show();
                } else if (currentPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your current password to confirm changes.", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.getText().toString().isEmpty() ||
                        !confirmPassword.getText().toString().equals(newPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Please make sure your passwords match.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Successfully changed password!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }
}