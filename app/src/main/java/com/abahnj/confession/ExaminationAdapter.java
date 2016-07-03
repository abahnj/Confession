package com.abahnj.confession;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;
import com.abahnj.confession.data.ConfessionContract.SinEntry;


/**
 * Created by abahnj on 7/2/2016.
 */
public class ExaminationAdapter extends RecyclerView.Adapter<ExaminationAdapter.EAViewHolder> {
    private Cursor mCursor;
    private static ExaminationClickListener clickListener;




    public interface ExaminationClickListener {

        void onClick(Uri examinationUri, int position);
    }

    public ExaminationAdapter (ExaminationClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public EAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_examination, parent, false);
        return new EAViewHolder(view); // return current item's ViewHolder
    }

    @Override
    public void onBindViewHolder(EAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(SinEntry._ID)));
        holder.mTextView1.setText(mCursor.getString(mCursor.getColumnIndex(SinEntry.COLUMN_DESCRIPTION)));
        holder.mTextView2.setText("0");
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
            mTextView1 = (TextView) itemView.findViewById(R.id.txtSin);
            mTextView2 = (TextView) itemView.findViewById(R.id.txtSinCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(ConfessionContract.CommandmentEntry.buildCommandmentUri(rowID), (int)rowID);
                }
            });
        }

        // set the database row ID for the contact in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }




    }

}
