package com.example.my2cents;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.*;

import com.google.firebase.FirebaseOptions;

public class recycler extends AppCompatActivity {
    public recycler() {}

    public recyclerAdapter mAdapter;
    private SQLiteDatabase db;
    private EditText recyclerText;
    private Button recyclerAdd;
    public int amount = 0;
    private EditText addChange;

    private FirebaseRecyclerOptions<recyclerAdapter> options;

    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(R.layout.recycler);

        recyclerAdd = findViewById(R.id.saveBtn);
        recyclerText = findViewById(R.id.recyclerTextView);
        addChange = findViewById(R.id.add_change);

        SQLHelper dbHelper = new SQLHelper(this);
        db = dbHelper.getWritableDatabase(); // must get writable database to add new items to it

        //connects recycler java to xml
        RecyclerView recyclerView = findViewById(R.id.recyclerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //layoutManager for recycler requires relative or linear layout
        mAdapter = new recyclerAdapter(this, getAllItems(db));
        recyclerView.setAdapter(mAdapter);

        /** Handles swipes from the left OR right (opportunity to do something different for left and right operations)**/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //ignore onMove for recycler implementation
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        recyclerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        amount++;
        if (recyclerText.getText().toString().trim().length() == 0 || amount == 0) {
            return; //cannot add empty item
        }
        String name = recyclerText.getText().toString();
        String balanceChange_userInput = addChange.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(SQLContract.SQLEntry.COLUMN_NAME, name); //adds text from textview to the SQL database
        cv.put(SQLContract.SQLEntry.COLUMN_AMOUNT, amount); //sets # of rows in database
        cv.put(SQLContract.SQLEntry.COLUMN_BALANCECHANGE, balanceChange_userInput); //sets the addition or subtraction of funds in the database from user input

        db.insert(SQLContract.SQLEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems(db));
        recyclerText.getText().clear(); //clears text field after entry is added
        addChange.getText().clear(); //clears addChange after entry is added
    }

    //for increase button if added
    private void increase() {
        amount++;
        recyclerText.setText(String.valueOf(amount));
    }
    //for decrease button if add
    private void decrease() {
        if (amount > 0) {
            amount--;
            recyclerText.setText(String.valueOf(amount));
        }
    }

    private void removeItem(long id) {
        db.delete(SQLContract.SQLEntry.TABLE_NAME,
                SQLContract.SQLEntry._ID + "=" + id, null); //TLDR: remove item with given ID
        mAdapter.swapCursor(getAllItems(db)); //swaps in new cursor as item is removed
    }

    //gets all items out of database for extraction to new database in next method
    public Cursor getAllItems(SQLiteDatabase database) {
        return database.query(
                    SQLContract.SQLEntry.TABLE_NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                SQLContract.SQLEntry.COLUMN_TIMESTAMP + " DESC" //descending order
        );
    }
}
