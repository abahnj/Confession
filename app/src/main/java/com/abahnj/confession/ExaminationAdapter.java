package com.abahnj.confession;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
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
    private static ExaminationClickListener clickListener;
    private Cursor mCursor;
    private Context context;


    public ExaminationAdapter (ExaminationClickListener clickListener, Context activity){
        ExaminationAdapter.clickListener = clickListener;
        context = activity;
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
        Resources res = context.getResources();
        int colorBlue;
        int colorWhite;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colorBlue = res.getColor(android.R.color.holo_blue_light, null);
            colorWhite = res.getColor(android.R.color.white, null);
        } else {
            colorBlue = res.getColor(android.R.color.holo_blue_light);
            colorWhite = res.getColor(android.R.color.white);

        }
        mCursor.moveToPosition(position);
        holder.setRowID(mCursor.getLong(mCursor.getColumnIndex(SinEntry._ID)));
        holder.mTextView1.setText(mCursor.getString(mCursor.getColumnIndex(SinEntry.COLUMN_DESCRIPTION)));
        final int count = mCursor.getInt(mCursor.getColumnIndex(ConfessionContract.PersonToSinEntry.COLUMN_COUNT));
        if(count != 0){
            holder.mTextView2.setBackgroundColor(colorBlue);
            holder.mTextView2.setTextColor(colorWhite);
        }
        holder.mTextView2.setText(String.valueOf(count));
        holder.setPosition(position);

        /*try {
        }
        catch (Resources.NotFoundException e) {
        holder.mTextView2.setText("0");
        }*/
    }

    @Override
    public int getItemCount() { return (mCursor != null) ? mCursor.getCount() : 0;    }

    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public interface ExaminationClickListener {

        void onClick(View v, int rowID, int position, boolean longClick);
    }

    public class EAViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;
        private long rowID;
        private int position;

        public EAViewHolder(View itemView) {
            super(itemView);
            mTextView1 = (TextView) itemView.findViewById(R.id.txtSin);
            mTextView2 = (TextView) itemView.findViewById(R.id.txtSinCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v, (int) rowID, position, false);
                }
            });
            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onClick(v, (int) rowID, position, true);
                    return true;
                }
            });*/
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onClick(v, (int) rowID, position, true);
                    return false;
                }
            });

            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0, 1, 0, R.string.context_menu_count);
                    menu.add(0, 2, 0, R.string.reset_count);
                    menu.add(0, 3, 0, R.string.context_menu_edit);
                    menu.add(0, 4, 0, R.string.context_menu_delete);
                    menu.add(0, 5, 0, R.string.context_menu_restore);

                }
            });

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
