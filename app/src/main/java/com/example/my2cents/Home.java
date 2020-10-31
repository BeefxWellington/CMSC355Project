package com.example.my2cents;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Home extends Fragment {

    ViewPager viewPager;
    HomeAdapter adapter;
    List<HomeModel> models;
    TabLayout tabLayout;
    FloatingActionButton fab1;
    Context context;
    Spinner typeSpinner;
    Spinner cateSpinner;
    EditText amount;
    Button save;
    Button cancel;
    Spinner mainCategories;
    Spinner subCategories;
    TextView amountBalance;
    TextView secondAmount;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    private FirebaseAuth firebaseAuth;
    int sumTotalInc = 0;

    String stringExpense;



    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //Set info to display for card view

        setModels();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        View v = inflater.inflate(R.layout.fragment_home, container, false);


        //Create and set adapter for pager
        //adapter = new HomeAdapter(models, this.getContext());
        viewPager = v.findViewById(R.id.viewPager);
      //  viewPager.setAdapter(adapter);
        //Connect dots indicator to pager
        tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);


        amountBalance = v.findViewById(R.id.balanceAmount);
        final FirebaseUser Users = firebaseAuth.getCurrentUser();
        String UserId = Users.getUid();





        //dhruv


        fab1 = v.findViewById(R.id.floatingActionButton1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }

        });

        return v;
    }



















    public void setModels() {
        models = new ArrayList<>();

        //Add temporary strings
        models.add(new HomeModel("Month Summary",
                "Starting Balance", "Total Expenses", "Upcoming Deductions",
                String.valueOf(sumTotalInc), stringExpense , "3",
                null, null, null));

        models.add(new HomeModel("Recent Expenses",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00", "$000.00", "$000.00",
                "MM/DD/YYYY", "MM/DD/YYYY", "MM/DD/YYYY"));

        models.add(new HomeModel("Upcoming Deductions",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00", "$000.00", "$000.00",
                "MM/DD/YYYY", "MM/DD/YYYY", "MM/DD/YYYY"));
        final FirebaseUser Users = firebaseAuth.getCurrentUser();
        String UserId = Users.getUid();
        databaseReference1 = databaseReference.child(UserId).child("Income");
        databaseReference2 = databaseReference.child(UserId).child("Expense");
        //amountBalance.setText(UserId);

        databaseReference2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sumExp=0;

                int pValue = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String amount = ds.child("amount").getValue(String.class);
                    pValue = Integer.parseInt(String.valueOf(amount));
                    sumExp += pValue;
                    final int sumTotalExp = sumExp;
                    // pValue = Integer.parseInt(String.valueOf(price));
                    //sumExp += pValue;

                    //amountBalance.setText(String.valueOf(sumExp));
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int sumInc=0;

                            int pValue = 0;
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                Map<String,Object> map = (Map<String, Object>) ds.getValue();
                                Object amountIncome = map.get("amount");

                                pValue = Integer.parseInt(String.valueOf(amountIncome));
                                sumInc += pValue;
                                sumTotalInc = sumInc;
                                int currentBalance = sumTotalInc - sumTotalExp;
                                amountBalance.setText(String.valueOf(currentBalance));
                            }
                            models.get(0).setFirstAmount(String.valueOf(sumTotalInc));

                            viewPager.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //databaseReference1 = databaseReference.child(UserId).child("Income");
        //databaseReference2 = databaseReference.child(UserId).child("Expense");
        //amountBalance.setText(UserId);

        databaseReference2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int sumExp= 0;

                int pValue = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String amount = ds.child("amount").getValue(String.class);
                    pValue = Integer.parseInt(String.valueOf(amount));
                    sumExp += pValue;
                    final int sumTotalExps = sumExp;
                    // pValue = Integer.parseInt(String.valueOf(amountExpense));
                    //sumExp += pValue;
                    //secondAmount.getText().toString();

                    stringExpense = String.valueOf(sumTotalExps);


                }
                models.get(0).setSecondAmount(stringExpense);

                adapter = new HomeAdapter(models, getContext());
                viewPager.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    // alert dialog that shows when floating action button is clicked
    public void showDialog() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View view2 = factory.inflate(R.layout.dialog_box, null);
        alertDialog.setView(view2);
        mainCategories = view2.findViewById(R.id.typeSpinner);
        subCategories = view2.findViewById(R.id.categorySpinner);
        amount = view2.findViewById(R.id.amountEt);
        save = view2.findViewById(R.id.saveBtn);


        final AlertDialog builder = alertDialog.create();

        //cancel button to close the alert dialog
        cancel = view2.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.cancel();
            }
        });

        builder.show();

        // spinner for input output
        typeSpinner = view2.findViewById(R.id.typeSpinner);
        List<String> typeOfInput = new ArrayList<String>();
        typeOfInput.add("Expense");
        typeOfInput.add("Income");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, typeOfInput);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        //spinner for categories
        cateSpinner = view2.findViewById(R.id.categorySpinner);
        List<String> typeOfCategories = new ArrayList<>();
        typeOfCategories.add("Bills");
        typeOfCategories.add("Food");
        typeOfCategories.add("Gas");
        typeOfCategories.add("Entertainment");
        typeOfCategories.add("Pay Check");
        typeOfCategories.add("Savings");
        typeOfCategories.add("Miscellaneous");
        typeOfCategories.add("Lottery");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeOfCategories);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cateSpinner.setAdapter(categoryAdapter);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpenses();

            }
        });
    }

    public void addExpenses() {

        String amountValue = amount.getText().toString();
        String mainCategoriesValue = mainCategories.getSelectedItem().toString();
        String subCategoriesValue = subCategories.getSelectedItem().toString();
        final FirebaseUser Users = firebaseAuth.getCurrentUser();
        String UserID = Users.getUid();

        if (!TextUtils.isEmpty(amountValue) && !TextUtils.isEmpty(mainCategoriesValue) && !TextUtils.isEmpty(subCategoriesValue)) {

            String ID = databaseReference.push().getKey();
            passingModel PassingModel = new passingModel(ID,mainCategoriesValue,subCategoriesValue,amountValue);
            databaseReference.child(UserID).child(mainCategoriesValue).child(ID).setValue(PassingModel);
            amount.setText("");
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
        }


    }

}