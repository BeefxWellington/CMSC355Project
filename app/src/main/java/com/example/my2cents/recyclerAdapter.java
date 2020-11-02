package com.example.my2cents;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/** The purpose of the recyclerAdapter is to update ui elements in the recycler item xml file for each new entry in the database that the user will see in the GUI**/
public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerViewHolder> {
    private final Context mContext;
    private Cursor mCursor;

    public recyclerAdapter(Context context, Cursor cursor) { //cursor gets data out of database //context creates context for each data that cursor gets
        mContext = context;
        mCursor = cursor;
    }

    public class recyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView timestampText;
        public TextView balanceChange;
        //public TextView countText; //use if using user facing counter (we are not)

        public recyclerViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_item);
            timestampText = itemView.findViewById(R.id.timestamp_item);
            balanceChange = itemView.findViewById(R.id.balance_change);
        }
    }

    //Visually adds new database entry onto the recycler
    @NonNull
    @Override
    public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.recycler_item, parent, false);
        return new recyclerViewHolder(v);
    }

    //displays data on created item
    @Override
    public void onBindViewHolder(@NonNull recyclerViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) { //ensures cursor exists for this position
            return;
        }

        /** The following lines obtain data from the SQLite database and assign the values to the variables linked to the UI elements in the recycler item**/
        String name = mCursor.getString(mCursor.getColumnIndex(SQLContract.SQLEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(SQLContract.SQLEntry.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(SQLContract.SQLEntry._ID));
        String timeStamp = mCursor.getString(mCursor.getColumnIndex(SQLContract.SQLEntry.COLUMN_TIMESTAMP));
        String balanceChange = mCursor.getString(mCursor.getColumnIndex(SQLContract.SQLEntry.COLUMN_BALANCECHANGE));


        holder.nameText.setText(name);
        holder.itemView.setTag(id); //passes id of item to recycler activity (i.e.: the recycler that the user sees
        holder.timestampText.setText(timeStamp);
        //holder.countText.setText(String.valueOf((amount))); // uncomment if using forward facing counter of total items
        holder.balanceChange.setText(balanceChange);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount(); // Gives quantity of items in database via cursor
    }

    //when database is updated, it is required that a new cursor is also built along with it
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

}
