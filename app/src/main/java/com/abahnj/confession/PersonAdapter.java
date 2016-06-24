package com.abahnj.confession;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract.PersonEntry;

/**
 * Created by abahnj on 6/5/2016.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PAViewHolder> {

private static PersonClickListener clickListener;
private Cursor mCursor;



public PersonAdapter(PersonClickListener clickListener) {
        this.clickListener = clickListener;
        }

// interface implemented by LoginActivity to respond
// when the user touches an item in the RecyclerView
public interface PersonClickListener {
    void onClick(Uri contactUri, int position);
}


    @Override
    public PAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        return new PAViewHolder(view); // return current item's ViewHolder
    }

    @Override
    public void onBindViewHolder(PAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(PersonEntry._ID)));
        holder.mTextView.setText(mCursor.getString(mCursor.getColumnIndex(
                PersonEntry.COLUMN_NAME)));
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

public class PAViewHolder extends RecyclerView.ViewHolder {
    public final TextView mTextView;
    private long rowID;



    public PAViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(android.R.id.text1);

        // attach listener to itemView
        itemView.setOnClickListener(
                new View.OnClickListener() {
                    // executes when the contact in this ViewHolder is clicked
                    @Override
                    public void onClick(View view) {
                        clickListener.onClick(PersonEntry.buildPersonUri(rowID), ((int) rowID));
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

