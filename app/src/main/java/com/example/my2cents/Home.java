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
import android.widget.ListView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    int sumTotalInc = 0;

    FirebaseDatabase rootNode;
    DatabaseReference refNode;
    Query checkData;
    final private ArrayList<String> testList = new ArrayList<>();
    private String day;
    private String dayNum;
    private String month;
    private String year;
    private String hour;
    private String min;
    private String sec;
    private DateFormat df;
    private String date;
    private Timestamp timeStamp;

    private String amountValue;
    private String mainCategoryValue;
    private String subCategoryValue;

    private ListView listView;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set info to display for card view
        setModels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (ListView) v.findViewById(R.id.logListView);

        String UserID;
        UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
//        listView = v.findViewById(R.id.listView);

        /******* Firebase Database Retrieval Code *******/
//        //firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("AccountEntry");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                testList.clear();
                for (DataSnapshot datasnapshot1 : snapshot.getChildren()) {
                    String day = datasnapshot1.child("amount").getValue(String.class);
                    testList.add(day);
                }
                //listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /******* Firebase Database Retrieval Code *******/
//        final ArrayAdapter listAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.test_list_view_item, testList);
//        listView.setAdapter(listAdapter);


        rootNode = FirebaseDatabase.getInstance();
        refNode = rootNode.getReference("AccountEntry");

        //Create and set adapter for pager
        adapter = new HomeAdapter(models, this.getContext());
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        //Connect dots indicator to pager
        tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
//
//
//        amountBalance = v.findViewById(R.id.balanceAmount);
//
//        amountBalance.setText(UserId);

//         databaseReference2.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int sumExp=0;
//
//                int pValue = 0;
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    String amount = ds.child("amount").getValue(String.class);
//                    pValue = Integer.parseInt(String.valueOf(amount));
//                    sumExp += pValue;
//                    final int sumTotalExp = sumExp;
//                    // pValue = Integer.parseInt(String.valueOf(price));
//                    //sumExp += pValue;
//
//                    //amountBalance.setText(String.valueOf(sumExp));
//                    databaseReference1.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            int sumInc=0;
//
//                            int pValue = 0;
//                            for(DataSnapshot ds : dataSnapshot.getChildren()){
//                                Map<String,Object> map = (Map<String, Object>) ds.getValue();
//                                Object amountIncome = map.get("amount");
//
//                                pValue = Integer.parseInt(String.valueOf(amountIncome));
//                                sumInc += pValue;
//                                sumTotalInc = sumInc;
//                                int currentBalance = sumTotalInc - sumTotalExp;
//                                amountBalance.setText(String.valueOf(currentBalance));
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

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
      /*  secondAmount = getActivity().findViewById(R.id.secondAmount);
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
                    final int sumTotalExps = sumExp;
                    // pValue = Integer.parseInt(String.valueOf(amountExpense));
                    //sumExp += pValue;

                    secondAmount.setText(String.valueOf(sumExp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */

        models = new ArrayList<>();

        //Add temporary strings
        models.add(new HomeModel("Month Summary",
                "Starting Balance", "Total Expenses", "Upcoming Deductions",
                "$000.00", "$000.00", "3",
                null, null, null));

        models.add(new HomeModel("Recent Expenses",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00", "$000.00", "$000.00",
                "MM/DD/YYYY", "MM/DD/YYYY", "MM/DD/YYYY"));

        models.add(new HomeModel("Upcoming Deductions",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00", "$000.00", "$000.00",
                "MM/DD/YYYY", "MM/DD/YYYY", "MM/DD/YYYY"));
    }

    // alert dialog that shows when floating action button is clicked
    public void showDialog() {
        rootNode = FirebaseDatabase.getInstance();
        refNode = rootNode.getReference("AccountEntry");
        DatabaseReference refNode = FirebaseDatabase.getInstance().getReference("Users");

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
        List<String> typeOfCategories = new ArrayList<>();
        addSpinnerListItems(typeOfInput, typeOfCategories);

        if (getActivity() != null) {
            ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, typeOfInput);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(typeAdapter);

            //spinner for categories
            cateSpinner = view2.findViewById(R.id.categorySpinner);
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeOfCategories);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cateSpinner.setAdapter(categoryAdapter);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpenses();

            }
        });

        refNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addSpinnerListItems(List<String> typeOfInput, List<String> typeOfCategories) {
        typeOfInput.add("Expense");
        typeOfInput.add("Income");

        typeOfCategories.add("Bills");
        typeOfCategories.add("Food");
        typeOfCategories.add("Gas");
        typeOfCategories.add("Entertainment");
        typeOfCategories.add("Pay Check");
        typeOfCategories.add("Savings");
        typeOfCategories.add("Miscellaneous");
        typeOfCategories.add("Lottery");
    }

    public void addExpenses() {
        amountValue = amount.getText().toString();
        double amountDouble = Double.parseDouble(amountValue);
        mainCategoryValue = mainCategories.getSelectedItem().toString();
        subCategoryValue = subCategories.getSelectedItem().toString();
        double currentBalanceDouble = 100.00;

        passingModel passModel = new passingModel(mainCategoryValue,subCategoryValue,amountValue,timeStamp);

        df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        date = df.format(Calendar.getInstance().getTime());
        String day = date.substring(0, 3);
        String dayNum = date.substring(5, 6);
        String month = date.substring(7, 10);
        String year = date.substring(11, 15);
        String hour = date.substring(16, 18);
        String min = date.substring(19, 21);
        String sec = date.substring(22, 24);
        timeStamp = new Timestamp(day, month, year, dayNum, hour, min, sec);
        timeStamp.setDay(date.substring(0, 3));
        timeStamp.setMonth(date.substring(7, 10));

        String[] currentMonth = new String[]{date.substring(7, 10)};
        String[] currentDay = new String[]{date.substring(0, 3)};
        String[] currentDayNum = new String[]{date.substring(5, 6)};
        String[] currentYear = new String[]{date.substring(11, 15)};
        String[] currentHour = new String[]{date.substring(16, 18)};
        String[] currentMin = new String[]{date.substring(19, 21)};
        String[] currentSec = new String[]{date.substring(22, 24)};
        String[] title = new String[]{"title"};
        String[] mainCat = new String[]{mainCategoryValue};
        String[] subCat = new String[]{subCategoryValue};

        double[] currentAmountValue = new double[]{amountDouble};
        double[] currentBalance = new double[]{currentBalanceDouble};

        String UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";

        if (!TextUtils.isEmpty(amountValue) && !TextUtils.isEmpty(mainCategoryValue) && !TextUtils.isEmpty(subCategoryValue)) {

            String ID = databaseReference.push().getKey();
            passingModel PassingModel = new passingModel(mainCategoryValue,subCategoryValue,amountValue,timeStamp);
            //databaseReference.child(UserID).child("AccountEntry").child(ID).setValue(PassingModel);
            amount.setText(testList.get(testList.size()-5));
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
        }

        passModel.setAmount(amountValue);
        passModel.setMainCategories(mainCategoryValue);
        passModel.setSubCategories(subCategoryValue);

//        AnalyticsLog analyticsLog = new AnalyticsLog();
//        analyticsLog.setLogListValues(currentDay, currentMonth, title, mainCat, subCat, currentAmountValue, currentBalance);

//        Log log = new Log("day", "day", "test", "subCategory", "mainCategory", "amountValue", "100.00");
//        ArrayList<Log> logList = new ArrayList<>();
//        logList.add(log);
//        LogListAdapter adapter = new LogListAdapter(this.getContext(), R.layout.analytics_log_list, logList);
//        listView = view2.findViewById(R.id.logListView);
//        listView.setAdapter(adapter);
    }

    public void retrieveData(DatabaseReference userRef) {

    }

}