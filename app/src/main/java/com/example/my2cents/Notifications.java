package com.example.my2cents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static Notifications newInstance(String param1, String param2) {
        Notifications fragment = new Notifications();
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

    private boolean incomeNotifications = true;
    private boolean expenseNotifications = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        Button b1 = (Button) view.findViewById(R.id.savenotifications);

        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch all = (Switch) view.findViewById(R.id.switch1);
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch income = (Switch) view.findViewById(R.id.switch2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch expense = (Switch) view.findViewById(R.id.switch3);


        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    incomeNotifications = true;
                    expenseNotifications = true;
                    income.setChecked(true);
                    expense.setChecked(true);
                } else {
                    incomeNotifications = false;
                    expenseNotifications = false;
                    income.setChecked(false);
                    expense.setChecked(false);
                }
            }
        });

        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    incomeNotifications = true;
                } else {
                    incomeNotifications = false;
                    all.setChecked(false);
                }
            }
        });

        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    expenseNotifications = true;
                } else {
                    expenseNotifications = false;
                    all.setChecked(false);
                }
            }
        });

        if (expenseNotifications && incomeNotifications) {
            all.setChecked(true);
        }

        Home homeFragment = new Home();

        Bundle bundle = new Bundle();
        bundle.putBoolean("incomeNotifications", incomeNotifications);
        bundle.putBoolean("expenseNotifications", expenseNotifications);

        homeFragment.setArguments(bundle);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }


}