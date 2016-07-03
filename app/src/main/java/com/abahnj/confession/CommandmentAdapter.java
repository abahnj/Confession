package com.abahnj.confession;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract.CommandmentEntry;

/**
 * Created by abahnj on 6/22/2016.
 */
public class CommandmentAdapter extends RecyclerView.Adapter<CommandmentAdapter.CAViewHolder> {

    private Cursor mCursor;
    private static CommandmentClickListener clickListener;


    public interface CommandmentClickListener {

        void onClick(Uri examinationUri, int position);
    }

    public CommandmentAdapter(CommandmentClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public CAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_2, parent, false);
        return new CAViewHolder(view); // return current item's ViewHolder
        }

    @Override
    public void onBindViewHolder(CommandmentAdapter.CAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(CommandmentEntry._ID)));
        holder.mTextView1.setText(mCursor.getString(mCursor.getColumnIndex(CommandmentEntry.COLUMN_COMMANDMENT)));
        holder.mTextView2.setText(mCursor.getString(mCursor.getColumnIndex(CommandmentEntry.COLUMN_TEXT)));

    }

    @Override
    public int getItemCount() { return (mCursor != null) ? mCursor.getCount() : 0;    }

    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class CAViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;
        private long rowID;


        public CAViewHolder(View itemView) {
            super(itemView);
            mTextView1 = (TextView) itemView.findViewById(android.R.id.text1);
            mTextView2 = (TextView) itemView.findViewById(android.R.id.text2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(CommandmentEntry.buildCommandmentUri(rowID), (int)rowID);
                }
            });
        }

        // set the database row ID for the contact in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }




    }

}

