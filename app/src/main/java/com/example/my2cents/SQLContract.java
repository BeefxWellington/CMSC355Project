package com.example.my2cents;

import android.provider.BaseColumns;

public class SQLContract {

    private SQLContract() { }

    //provides ID column for table
    public static final class SQLEntry implements BaseColumns {

        public static final String TABLE_NAME = "recycler";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_AMOUNT = "quantity";
        public static final String COLUMN_TIMESTAMP = "timestamp"; //used to order items
        public static final String COLUMN_BALANCECHANGE = "balance_change"; //used to list balance_change
        public static final String COLUMN_BALANCE = "balance";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_CATEGORY = "category";
    }
}
