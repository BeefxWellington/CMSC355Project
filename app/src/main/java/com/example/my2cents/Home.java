package com.example.my2cents;

import android.content.ContentValues;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
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

    /** Firebase Database Code **/
    FirebaseDatabase rootNode;
    DatabaseReference refNode;
    DatabaseReference refLocation;


    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Set info to display for card view
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
                /** Firebase Database Code **/
                rootNode = FirebaseDatabase.getInstance();
                refNode = rootNode.getReference("AccountEntry");

                String name = typeSpinner.getSelectedItem().toString();
                String category = cateSpinner.getSelectedItem().toString();
                String inputAmount = amount.getText().toString();

                AccountEntry accountEntry = new AccountEntry(name, category, inputAmount);
                String UID = refNode.push().getKey();
                refNode.setValue(accountEntry);

                //addItem();
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
}