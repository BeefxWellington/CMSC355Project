package com.example.my2cents;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Home extends Fragment {

    private static Home home;

    ViewPager viewPager;
    HomeAdapter adapter;
    List<HomeModel> models;
    TabLayout tabLayout;
    FloatingActionButton fab1;
    FloatingActionButton help;
    Context context;
    Spinner typeSpinner;
    Spinner cateSpinner;
    EditText amount;
    Button save;
    Button cancel;
    Spinner mainCategories;
    Spinner subCategories;
    TextView amountBalance;
    EditText datePicker;
    DatePickerDialog.OnDateSetListener setListener;
    int selectedMonth, selectedYear, selectedDay;
    int numOfUpcomingdeductions;

    DatabaseReference databaseReference;
    DatabaseReference userRef;
    private String ID;

    FirebaseDatabase rootNode;
    DatabaseReference refNode;
    Query checkData;
    final public ArrayList<passingModel> testList = new ArrayList<>();
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
    private String upcomingDeductions;

    private String amountValue;
    private String mainCategoryValue;
    private String subCategoryValue;

    private String dbAmount;
    private String dbMainCat;
    private String dbSubCat;
    private ListView listView;
    private passingModel passModel;

    private double amountDouble;
    private double startingBalance;
    private double totalExpenses;

    boolean incomeNotifications = true;
    boolean expenseNotifications = true;



    public interface ActToFrag {
        public boolean getIncomeSwitchState();
        public boolean getExpenseSwitchState();
    }

    ActToFrag actToFrag;

    public Home() {
        if (home != null) {
            try {
                throw new Exception("test home message");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        amountDouble = 0;
        amountBalance = v.findViewById(R.id.balanceAmount);
        final AnalyticsLog analyticsLog = AnalyticsLog.getInstance();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                testList.clear();
                for (DataSnapshot datasnapshot1 : snapshot.getChildren()) {
                    ID = datasnapshot1.getKey();
                    dbAmount = datasnapshot1.child("amount").getValue(String.class);
                    dbMainCat = datasnapshot1.child("mainCategories").getValue(String.class);
                    dbSubCat = datasnapshot1.child("subCategories").getValue(String.class);
                    timeStamp = datasnapshot1.child("timeStamp").getValue(Timestamp.class);
                    upcomingDeductions = datasnapshot1.child("upcomingDedctions").getValue(String.class);
                    passModel = new passingModel(ID, dbMainCat, dbSubCat, dbAmount, timeStamp, upcomingDeductions);
                    testList.add(passModel);
                    if (dbMainCat.equals("Expense")) {
                        amountDouble -= Double.parseDouble(dbAmount);
                    }
                    else {
                        amountDouble += Double.parseDouble(dbAmount);
                    }
                }
                double[] newSetBalance = new double[]{amountDouble};
                amountBalance.setText("$" + Double.toString(amountDouble));
                analyticsLog.setTestList(testList);
                analyticsLog.setBalance(newSetBalance);
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

        fab1 = v.findViewById(R.id.floatingActionButton1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();
            }
        });

        help = v.findViewById(R.id.helpbttn);
        help.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent onboardingIntent = new Intent(getContext(), onboarding.class);
                startActivity(onboardingIntent);
            }
        });


        return v;
    }

    public void setModels() {
//
        String UserID;
        UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("AccountEntry");
        startingBalance = 0;
        totalExpenses = 0;
        numOfUpcomingdeductions=0;
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datasnapshot1 : snapshot.getChildren()) {
                    ID = datasnapshot1.getKey();
                    dbAmount = datasnapshot1.child("amount").getValue(String.class);
                    dbMainCat = datasnapshot1.child("mainCategories").getValue(String.class);
                    String upcomingDate = datasnapshot1.child("upcomingDeductions").getValue(String.class);
                    if (dbMainCat.equals("Income")) {
                        startingBalance += Double.parseDouble(dbAmount);
                    }
                    if (dbMainCat.equals("Expense")){
                        totalExpenses += Double.parseDouble(dbAmount);

                    }
                    if (upcomingDate != null){
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = dateFormat.parse(upcomingDate);
                            if (date.after(Calendar.getInstance().getTime())){
                                numOfUpcomingdeductions++;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

                models.get(0).setThirdAmount(String.valueOf(numOfUpcomingdeductions));


                models.get(0).setFirstAmount("$" + String.valueOf(startingBalance));
                viewPager.setAdapter(adapter);
                models.get(0).setSecondAmount("$" + String.valueOf(totalExpenses));

                adapter = new HomeAdapter(models, getContext());
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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
        datePicker = view2.findViewById((R.id.date_et));

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
        // data picker
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String Date = dayOfMonth+"/"+month+"/"+year;
                datePicker.setText(Date);
                selectedMonth = month;
                selectedYear = year;
                selectedDay = dayOfMonth;
                upcomingDeductions = Date;
            }
        };

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpenses();
                builder.cancel();

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

        //passingModel passModel = new passingModel(mainCategoryValue,subCategoryValue,amountValue,timeStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth- 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);



        df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        date = df.format(Calendar.getInstance().getTime());
        String day = date.substring(0, 3);
        String dayNum = date.substring(5, 7);
        String month = date.substring(8, 11);
        String year = date.substring(12, 16);
        String hour = date.substring(17, 19);
        String min = date.substring(20, 22);
        String sec = date.substring(23, 25);
        timeStamp = new Timestamp(day, month, year, dayNum, hour, min, sec);

        String UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";

        if (!TextUtils.isEmpty(amountValue) && !TextUtils.isEmpty(mainCategoryValue) && !TextUtils.isEmpty(subCategoryValue)) {

            String ID = databaseReference.push().getKey();
            passingModel PassingModel = new passingModel(ID, mainCategoryValue,subCategoryValue,amountValue,timeStamp, upcomingDeductions);
            databaseReference.child(UserID).child("AccountEntry").child(ID).setValue(PassingModel);
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            if (mainCategoryValue.equals("Expense") && expenseNotifications) {
                sendNotification("1", "EXPENSE_NOTIFICATIONS",
                        "$" + amountValue + " deducted.",
                        "Current balance: " + amountBalance.getText().toString());
            } else if (mainCategoryValue.equals("Income") && incomeNotifications) {
                sendNotification("2", "INCOME NOTIFICATIONS",
                        "$" + amountValue + " added.",
                        "Current balance: " + amountBalance.getText().toString());
            }
        }
        else {
            Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
        }

        passModel.setAmount(amountValue);
        passModel.setMainCategories(mainCategoryValue);
        passModel.setSubCategories(subCategoryValue);


    }

    public static Home getInstance() {
        if (home == null) {
            home = new Home();
        }
        return home;
    }


    public void sendNotification(String channelId, String channelName, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getContext(), channelId);
        } else {
            builder = new NotificationCompat.Builder(getContext());
        }
        builder.setSmallIcon(R.drawable.my2centstransparent)
                .setColor(ContextCompat.getColor(getActivity(), R.color.logoRed))
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(0, builder.build());
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        actToFrag = (ActToFrag) activity;
        incomeNotifications = actToFrag.getIncomeSwitchState();
        expenseNotifications = actToFrag.getExpenseSwitchState();
    }
}