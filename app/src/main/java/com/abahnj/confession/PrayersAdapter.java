package com.abahnj.confession;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * Created by abahnj on 7/8/2016.
 */
public class PrayersAdapter extends RecyclerView.Adapter<PrayersAdapter.PRAViewHolder> {

    private static PrayersClickListener clickListener;
    private Cursor mCursor;

    public PrayersAdapter(PrayersClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public PRAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        return new PRAViewHolder(view); // return current item's ViewHolder
    }

    @Override
    public void onBindViewHolder(PRAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(ConfessionContract.PrayersEntry._ID)));
        holder.mTextView.setText(mCursor.getString(mCursor.getColumnIndex(
                ConfessionContract.PrayersEntry.COLUMN_PRAYERNAME)));
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public interface PrayersClickListener {
        void onClick(Uri prayersUri, int position);
    }

    public class PRAViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;
        private long rowID;

        public PRAViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);

            // attach listener to itemView
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        // executes when the contact in this ViewHolder is clicked
                        @Override
                        public void onClick(View view) {
                            clickListener.onClick(ConfessionContract.PrayersEntry.buildPrayersUri(rowID), ((int) rowID));
                        }
                    }
            );
        }

        // set the database row ID for the contact in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }

}

