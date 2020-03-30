package com.example.reminder;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

/**
 * Created by engMa_000 on 2017-04-03.
 */

public class RemindersSimpleCursorAdapter extends SimpleCursorAdapter {

    public RemindersSimpleCursorAdapter(Context context, int layout, Cursor c, String[]
            from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_ID));
        String content = cursor.getString(cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_CONTENT));
        int imp = cursor.getInt(cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_IMPORTANT));
        Reminder reminder = new Reminder(id,content,imp);
        return reminder;
    }

    //to use a viewholder, you must override the following two methods and define a ViewHolder class
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.colImp = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_IMPORTANT);
            holder.colContent = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_CONTENT);
            holder.colID = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_ID);

            holder.listTab = view.findViewById(R.id.reminder_item_isImportant);
            holder.content = view.findViewById(R.id.reminder_item_text);
            holder.id = view.findViewById(R.id.reminder_item_id);
            view.setTag(holder);
        }
        if (cursor.getInt(holder.colImp) > 0) {
            holder.listTab.setBackgroundColor(ContextCompat.getColor(context,R.color.colorOrange));
        } else {
            holder.listTab.setBackgroundColor(ContextCompat.getColor(context,R.color.colorGreen));
        }

        holder.listTab.setText("");
        holder.id.setText(String.valueOf(cursor.getInt(holder.colID)));
        //if it didnt work use this
        //holder.id.setText(cursor.getString(holder.colID));

        holder.content.setText(cursor.getString(holder.colContent));

    }


    static class ViewHolder {
        //store the column index
        int colID;
        int colImp;
        int colContent;


        //store the view
        TextView listTab;

        //store the text
        TextView content;

        //store the id
        TextView id;

    }

}
