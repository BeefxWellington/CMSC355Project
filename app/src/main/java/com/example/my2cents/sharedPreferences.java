package com.example.my2cents;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class sharedPreferences<Example> extends AppCompatActivity {

    ArrayList<Example> testList;
    final TextView sharedPrefTextView = findViewById(R.id.sharePrefTextView);
    final Switch switch1 = findViewById(R.id.switch1);
    final Button saveData = findViewById(R.id.saveData);

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TEXT = "text";
    private static final String SWITCH1 = "switch1";
    private static final String KEY = "test list";

    private String text;
    private boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        loadData();
        updateViews();
    }
    //saveData is needed for permanent storage
    private void saveData() {
        /** Universal code for shared preferences **/
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE); //mode_private = allows no other app to change shared preferences
        SharedPreferences.Editor sharedPrefEdit = sharedPref.edit();

        /** Code specific to recycler view project **/
        Gson gsonSaveData = new Gson();
        String json = gsonSaveData.toJson(testList);
        sharedPrefEdit.putString(KEY, json);
        sharedPrefEdit.apply();

        /** Code specific to simple text permanence app **/
        sharedPrefEdit.putString(TEXT, sharedPrefTextView.getText().toString());
        sharedPrefEdit.putBoolean(SWITCH1, switch1.isChecked());

        sharedPrefEdit.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    //loadData is needed for permanent storage
    private void loadData() {
        /** Universal code for shared preferences **/
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        /** Code specific to recycler view project **/
        Gson gsonLoadData = new Gson();
        String json = sharedPref.getString(KEY, null);
        Type type = new TypeToken<ArrayList<Example>>() {}.getType();
        testList = gsonLoadData.fromJson(json, type);

        if (testList==null) {
            testList = new ArrayList<>();
        }

        /** Code specific to simple text permanence app **/
        text = sharedPref.getString(TEXT, "");
        switchOnOff = sharedPref.getBoolean(SWITCH1, false);
    }

    private void updateViews() {
        sharedPrefTextView.setText(text);
        switch1.setChecked(switchOnOff);
    }

}