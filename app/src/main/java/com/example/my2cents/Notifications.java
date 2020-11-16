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
public class Notifications extends android.app.Fragment {

    boolean incomeNotifications = true;
    boolean expenseNotifications = true;

    @SuppressLint("UseSwitchCompatOrMaterialCode") Switch all;
    @SuppressLint("UseSwitchCompatOrMaterialCode") Switch income;
    @SuppressLint("UseSwitchCompatOrMaterialCode") Switch expense;
    Button saveNotifications;

    public Notifications() {
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
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        saveNotifications = (Button) view.findViewById(R.id.savenotifications);

        all = (Switch) view.findViewById(R.id.switch1);
        income = (Switch) view.findViewById(R.id.switch2);
        expense = (Switch) view.findViewById(R.id.switch3);


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


        saveNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }


}