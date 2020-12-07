package com.example.my2cents;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class Notifications extends Fragment {

    public interface FragToAct {
        public void passSwitchState (boolean b1, boolean b2);
    }

    FragToAct fragToAct;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private @SuppressLint("UseSwitchCompatOrMaterialCode") Switch all;
    private @SuppressLint("UseSwitchCompatOrMaterialCode") Switch income;
    private @SuppressLint("UseSwitchCompatOrMaterialCode") Switch expense;
    private Button saveNotifications;

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

        sharedPreferences = getActivity().getSharedPreferences("SharedPreferences", getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();

        all.setChecked(sharedPreferences.getBoolean("allSwitch", true));
        income.setChecked(sharedPreferences.getBoolean("incomeSwitch", true));
        expense.setChecked(sharedPreferences.getBoolean("expenseSwitch", true));

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("allSwitch", b).commit();
                if (b) {
                    income.setChecked(true);
                    expense.setChecked(true);
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!all.isChecked()) {
                    income.setChecked(false);
                    expense.setChecked(false);
                }
            }
        });

        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("incomeSwitch", b).commit();
                if (b) {
                    if (b && expense.isChecked()) {
                        all.setChecked(true);
                    }
                } else {
                    all.setChecked(false);
                }
            }
        });

        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("expenseSwitch", b).commit();
                if (b) {
                    if (b && income.isChecked()) {
                        all.setChecked(true);
                    }
                } else {
                    all.setChecked(false);
                }
            }
        });


        saveNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();

                fragToAct.passSwitchState(sharedPreferences.getBoolean("incomeSwitch", true),
                        sharedPreferences.getBoolean("expenseSwitch", true));

            }
        });

        return view;

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragToAct = (FragToAct) context;
    }
}

