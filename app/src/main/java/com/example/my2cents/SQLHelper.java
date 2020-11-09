package com.example.my2cents;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.my2cents.SQLContract.SQLEntry;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLlist.db";
    // DATABASE_VERSION is only used for updating the schema of the database (i.e.: adding a new column)
    // upon which the version number should be incremented
    public static final int DATABASE_VERSION = 2;

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SQLIST_TABLE = "CREATE TABLE " +
                SQLEntry.TABLE_NAME + " (" +
                SQLEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + //the ID is an integer, which is also a primary key (also a unique identifier for each row, and auto increments for each row added
                SQLEntry.COLUMN_NAME + " TEXT NOT NULL, " + //String = text in SQLite. "NOT NULL" the string cannot be empty
                SQLEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " + //input cannot be negative for quantity
                SQLEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " + //a timestamp will be added for each new item/row
                SQLEntry.COLUMN_BALANCECHANGE + " INTEGER NOT NULL, " + //adds balance_change to the database
                SQLEntry.COLUMN_BALANCE + " INTEGER, " + //adds current balance to the database
                SQLEntry.COLUMN_TYPE + " TEXT, " + //adds column for expenditure/income identification
                SQLEntry.COLUMN_CATEGORY + " TEXT " + //adds column for categorization of database entries
                ");";
        db.execSQL(SQL_CREATE_SQLIST_TABLE); //executes when database needs to be created for the first time, creates the database
    }

    // if the version number is incremented, then all new code to add to new version should be put here
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQLEntry.TABLE_NAME);
        onCreate(db);
    }
}
