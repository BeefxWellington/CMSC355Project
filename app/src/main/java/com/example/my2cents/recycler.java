package com.example.my2cents;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class recycler extends AppCompatActivity {
    private SQLiteDatabase db;
    private TextView recyclerText;
    private Button recyclerAdd;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(R.layout.recycler);

        SQLHelper dbHelper = new SQLHelper(this);
        db = dbHelper.getWritableDatabase(); // must get writable database to add new items to it

        recyclerAdd = findViewById(R.id.recyclerAdd);
        recyclerText = findViewById(R.id.recyclerTextView);

        recyclerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        if (recyclerText.getText().toString().trim().length() == 0 || amount == 0) {
            return; //cannot add empty item
        }
        String name = recyclerText.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(SQLContract.SQLEntry.COLUMN_NAME, name); //adds text from textview to the SQL database
        cv.put(SQLContract.SQLEntry.COLUMN_AMOUNT, amount); //sets # of rows in database

        db.insert(SQLContract.SQLEntry.TABLE_NAME, null, cv);
        recyclerText.setText("");
    }
    private void increase() {
        amount++;
        recyclerText.setText(String.valueOf(amount));
    }
    private void decrease() {
        if (amount > 0) {
            amount--;
            recyclerText.setText(String.valueOf(amount));
        }
    }
}
