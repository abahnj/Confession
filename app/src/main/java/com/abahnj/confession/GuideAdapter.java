package com.abahnj.confession;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abahnj.confession.data.ConfessionContract;

/**
 * Created by abahnj on 8/19/2016.
 */
public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GAViewHolder>{
    private Cursor mCursor;
    private static GuideClickListener clickListener;

    public interface GuideClickListener {
        void onClick( int position);
    }

    public GuideAdapter(GuideClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public GAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
               android.R.layout.simple_selectable_list_item, parent, false);
        return new GAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GAViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getInt(mCursor.getColumnIndex(ConfessionContract.GuideEntry._ID)));
        holder.mTextView1.setText(mCursor.getString(mCursor.getColumnIndex(ConfessionContract.GuideEntry.COLUMN_GUIDE_TITLE)));
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }


    public void swapCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class GAViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView1;
        private int rowID;

        public GAViewHolder(View itemView) {
            super(itemView);

            mTextView1 =(TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(rowID);
                }
            });

        }
        public void setRowID(int rowID) {
            this.rowID = rowID;
        }

    }
}
