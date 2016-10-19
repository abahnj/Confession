package com.abahnj.confession;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract.CommandmentEntry;

/**
 * Created by abahnj on 6/22/2016.
 */
public class CommandmentAdapter extends RecyclerView.Adapter<CommandmentAdapter.CAViewHolder> {

    private static CommandmentClickListener clickListener;
    private Cursor mCursor;


    public CommandmentAdapter(CommandmentClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public CAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_commandments, parent, false);
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

    public interface CommandmentClickListener {

        void onClick(Uri examinationUri, int position);
    }

    public class CAViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;
        private long rowID;


        public CAViewHolder(View itemView) {
            super(itemView);
            mTextView1 = (TextView) itemView.findViewById(R.id.commandment_heading);
            mTextView2 = (TextView) itemView.findViewById(R.id.commandment_subheading);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(CommandmentEntry.buildCommandmentUri(rowID), (int)rowID);
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v
                                .findViewById(R.id.row_contentC)
                                .getBackground()
                                .setHotspot(event.getX(), event.getY());
                        return (false);
                    }
                });
            }
        }

        // set the database row ID for the contact in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }




    }

}

