package com.example.my2cents;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public recyclerAdapter(Context context, Cursor cursor) { //cursor gets data out of database //context creates context for each data that cursor gets
        mContext = context;
        mCursor = cursor;
    }

    public class recyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        //public TextView countText; //use if using user facing counter (we are not)

        public recyclerViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.recyclerTextView);
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
        if (!mCursor.move(position)) { //ensures cursor exists for this position
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(SQLContract.SQLEntry.COLUMN_NAME);
        int amount = mCursor.getInt(mCursor.getColumnIndex(SQLContract.SQLEntry.COLUMN_AMOUNT));
        holder.nameText.setText(name);
        //holder.countText.setText(String.valueOf((amount))); // uncomment if using forward facing counter of total items
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount(); // Gives quantity of items in database via cursor
    }

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
