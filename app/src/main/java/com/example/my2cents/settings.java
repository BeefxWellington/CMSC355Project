package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
    public static settings newInstance(String param1, String param2) {
        settings fragment = new settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button b1 = (Button)view.findViewById(R.id.button_notifsettings);
        Button b2 = (Button)view.findViewById(R.id.button_passsettings);
        Button b3 = (Button)view.findViewById(R.id.button_userguide);
        Button b4 = (Button)view.findViewById(R.id.button_usersettings);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);

        switch(view.getId()) {
            case R.id.button_notifsettings:
                ft.replace(((ViewGroup)getView().getParent()).getId(), new Notifications()).commit();
                break;
            case R.id.button_usersettings:
                ft.replace(((ViewGroup)getView().getParent()).getId(), new UserSettings()).commit();
                break;
            case R.id.button_passsettings:
                ft.replace(((ViewGroup)getView().getParent()).getId(), new PasswordSettings()).commit();
                break;
            case R.id.button_userguide:
                ft.replace(((ViewGroup)getView().getParent()).getId(), new UserGuide()).commit();
                break;
        }
    }

}