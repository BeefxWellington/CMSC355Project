package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class settings extends Fragment {

    Button notificationSettings;
    Button passwordSettings;
    Button usernameSettings;

    public settings() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationSettings = (Button)view.findViewById(R.id.button_notifsettings);
        notificationSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_notifications2, null));

        passwordSettings = (Button)view.findViewById(R.id.button_passsettings);
        passwordSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_passwordSettings2, null));

        usernameSettings = (Button)view.findViewById(R.id.button_usersettings);
        usernameSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settings_to_userSettings2, null));

        return view;
    }


}