package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordSettings extends Fragment {

    EditText newPassword;
    EditText currentPassword;
    EditText confirmPassword;
    EditText email;
    private FirebaseAuth firebaseAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordSettings() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_settings, container, false);
        Button b1 = (Button) view.findViewById(R.id.changepassword);
       /* newPassword = (EditText) view.findViewById(R.id.newPasswordEditText);
        currentPassword = (EditText) view.findViewById(R.id.currentPasswordEditText);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPasswordEditText); */
        email = view.findViewById(R.id.emailReset);
        firebaseAuth = FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (newPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a new password.", Toast.LENGTH_SHORT).show();
                } else if (currentPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your current password to confirm changes.", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.getText().toString().isEmpty() ||
                        !confirmPassword.getText().toString().equals(newPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Please make sure your passwords match.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Successfully changed password!", Toast.LENGTH_SHORT).show();
                    ((SecondActivity)getActivity()).onBackPressed();
                } */
                String mail = email.getText().toString();
                firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "One Moment",Toast.LENGTH_SHORT).show();
                        ((SecondActivity)getActivity()).onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error",Toast.LENGTH_SHORT).show();

                    }
                });

              // getFragmentManager().popBackStack();


            }
        });

        return view;
    }
}