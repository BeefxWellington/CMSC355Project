package com.example.my2cents;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;


public class settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button notificationSettings;
    Button passwordSettings;
    Button userGuide;
    Button usernameSettings;

    private FirebaseAuth firebaseAuth;
    public settings() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationSettings = (Button)view.findViewById(R.id.button_notifsettings);
        notificationSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_notifications2, null));

        passwordSettings = (Button)view.findViewById(R.id.button_passsettings);
        passwordSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_passwordSettings2, null));

        userGuide = (Button)view.findViewById(R.id.button_userguide);
        userGuide.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_userGuide2, null));

        usernameSettings = (Button)view.findViewById(R.id.button_usersettings);
        usernameSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_userSettings2, null));

        final Button signout = view.findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return view;
    }



}