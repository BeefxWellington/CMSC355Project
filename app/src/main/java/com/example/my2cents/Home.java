package com.example.my2cents;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    ViewPager viewPager;
    HomeAdapter adapter;
    List<HomeModel> models;
    TabLayout tabLayout;
    FloatingActionButton fab1;
    Context mContext;
    Spinner typeSpinner;
    Spinner cateSpinner;
    EditText amount;
    Button save;
    Button cancel;

    /** SQLite Database variables**/
    private int entryAmount;
    private static String selectedItem;
    public SQLiteDatabase db;
    private int balance = 0;
    TextView balanceAmount;

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference income;
    DatabaseReference expense;
    double amount1;
    boolean incomeNotifications;
    boolean expenseNotifications;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Set info to display for card view
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        setModels();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(mContext);
//        mContext = context;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container,false);

        /** SQLite Datbase Code **/
//        SQLHelper dbHelper = new SQLHelper(getActivity());
////        db = dbHelper.getWritableDatabase(); // must get writable database to add new items to it
////        RecyclerView recyclerView = v.findViewById(R.id.recyclerRecycler);
////        recyclerView.setLayoutManager(new LinearLayoutManager(recycler);
        balanceAmount = v.findViewById(R.id.balanceAmount);

        //Create and set adapter for pager
        adapter = new HomeAdapter(models, this.getContext());
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        //Connect dots indicator to pager
        tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

            //dhruv
        //typeSpinner= v.findViewById(R.id.typeSpinner);
        //cateSpinner = v.findViewById(R.id.categorySpinner);
        amount = v.findViewById(R.id.amountEt);
        save = v.findViewById(R.id.saveBtn);
        //cancel = v.findViewById(R.id.cancelBtn);
        fab1 = v.findViewById(R.id.floatingActionButton1);

        /*typeSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> typeCategories = new ArrayList<String>();
        typeCategories.add("Expense");
        typeCategories.add("Income");

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeCategories);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);*/
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialog();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser Users = firebaseAuth.getCurrentUser();

        String id = Users.getUid();

        income = databaseReference.child(id).child("Income");
        expense = databaseReference.child(id).child("Expense");


        incomeNotifications = getArguments().getBoolean("incomeNotifications");
        expenseNotifications = getArguments().getBoolean("expenseNotifications");

        income.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(incomeNotifications) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        amount1 = Double.parseDouble(snapshot.child("amount").getValue(String.class));
                    }
                    notification("$" + amount1 + " was added to your account.");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        expense.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (expenseNotifications) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        amount1 = Double.parseDouble(snapshot.child("amount").getValue(String.class));
                    }
                    notification("$" + amount1 + " was deducted from your account.");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;



    }

    public void setModels(){
        models = new ArrayList<>();

        //Add temporary strings
        models.add(new HomeModel("Month Summary",
                "Starting Balance", "Total Expenses", "Upcoming Deductions",
                "$000.00", "$000.00", "3",
                null, null, null));

        models.add(new HomeModel("Recent Expenses",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00","$000.00","$000.00",
                "MM/DD/YYYY","MM/DD/YYYY","MM/DD/YYYY"));

        models.add(new HomeModel("Upcoming Deductions",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00","$000.00","$000.00",
                "MM/DD/YYYY","MM/DD/YYYY","MM/DD/YYYY"));
    }

    public void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View view2 = factory.inflate(R.layout.dialog_box, null);
        alertDialog.setView(view2);

        amount = view2.findViewById(R.id.amountEt);

        final AlertDialog builder = alertDialog.create();
        cancel = view2.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.cancel();
            }
        });

        save = view2.findViewById(R.id.saveBtn);

        builder.show();

        typeSpinner = view2.findViewById(R.id.typeSpinner);
        List<String> typeOfInput = new ArrayList<String>();
        typeOfInput.add("Expense");
        typeOfInput.add("Income");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, typeOfInput);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);


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
            public void onClick(View view) {
                addItem();
                //amount.setText(selectedItem);
            }
        });

        selectedItem = typeSpinner.getSelectedItem().toString();
    }

    /** SQL Database code below **/
    private void addItem() {
        entryAmount++;
        ContentValues cv = new ContentValues();
        String name = typeSpinner.getSelectedItem().toString();
        String category = cateSpinner.getSelectedItem().toString();
        String inputAmount = amount.getText().toString();
        cv.put(SQLContract.SQLEntry.COLUMN_NAME, name);
        cv.put(SQLContract.SQLEntry.COLUMN_AMOUNT, entryAmount);
        cv.put(SQLContract.SQLEntry.COLUMN_BALANCECHANGE, inputAmount);
        cv.put(SQLContract.SQLEntry.COLUMN_TYPE, name);
        cv.put(SQLContract.SQLEntry.COLUMN_CATEGORY, category);

        //db.insert(SQLContract.SQLEntry.TABLE_NAME, null, cv);
        amount.getText().clear();
        updateBalance();

//        recycler referenceRecycler = new recycler();
//
//        referenceRecycler.getAllItems(db);
//        referenceRecycler.mAdapter.swapCursor(referenceRecycler.getAllItems(db));
    }

    private void updateBalance() {
        if (typeSpinner.getSelectedItem().toString() == "income") {
            int inputAmount = Integer.parseInt(amount.getText().toString());
            balance += inputAmount;
        }
        String newBalanceAmount;
        balanceAmount.setText(Integer.toString(balance));
    }

    public void notification(String message) {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelId = "notification";
            NotificationChannel notificationChannel = null;
            notificationChannel = new NotificationChannel(channelId, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("My2Cents")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationManager.notify(0, builder.build());
    }
}