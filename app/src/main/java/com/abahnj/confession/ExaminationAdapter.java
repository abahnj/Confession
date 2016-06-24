package com.abahnj.confession;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * Created by abahnj on 6/22/2016.
 */
public class ExaminationAdapter extends RecyclerView.Adapter<ExaminationAdapter.EAViewHolder> {

    private Cursor mCursor;


    @Override
    public EAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_2, parent, false);
        return new EAViewHolder(view); // return current item's ViewHolder
        }

    @Override
    public void onBindViewHolder(ExaminationAdapter.EAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(ConfessionContract.CommandmentEntry._ID)));
        holder.mTextView1.setText(mCursor.getString(mCursor.getColumnIndex(
                ConfessionContract.CommandmentEntry.COLUMN_COMMANDMENT)));
        holder.mTextView2.setText(mCursor.getString(mCursor.getColumnIndex(
                ConfessionContract.CommandmentEntry.COLUMN_TEXT)));

    }

    @Override
    public int getItemCount() { return (mCursor != null) ? mCursor.getCount() : 0;    }

    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class EAViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;
        private long rowID;


        public EAViewHolder(View itemView) {
            super(itemView);
            mTextView1 = (TextView) itemView.findViewById(android.R.id.text1);
            mTextView2 = (TextView) itemView.findViewById(android.R.id.text2);
        }

        // set the database row ID for the contact in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }

    }

}

