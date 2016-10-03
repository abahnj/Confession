package com.abahnj.confession;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * Created by abahnj on 7/25/2016.
 */
public class ConfessionAdapter extends RecyclerView.Adapter<ConfessionAdapter.CcAViewHolder> {


    private Cursor mCursor;

    @Override
    public CcAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_examination, parent, false);
        return new CcAViewHolder(view); // return current item's ViewHolder

    }

    @Override
    public void onBindViewHolder(CcAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(ConfessionContract.SinActiveEntry._ID)));
        holder.mTextView1.setText(mCursor.getString(mCursor.getColumnIndex(ConfessionContract.SinActiveEntry.COLUMN_DESCRIPTION)));
        final int count = mCursor.getInt(mCursor.getColumnIndex(ConfessionContract.PersonToSinEntry.COLUMN_COUNT));
        holder.mTextView2.setText(String.valueOf(count));
        holder.setPosition(position);

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

    public class CcAViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;
        private long rowID;
        private int position;

        public CcAViewHolder(View itemView) {
            super(itemView);
            mTextView1 = (TextView) itemView.findViewById(R.id.txtSin);
            mTextView2 = (TextView) itemView.findViewById(R.id.txtSinCount);


        }


        // set the database row ID for the examination in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }

        public void setPosition(int position) {
            this.position = position;
        }


    }

}
