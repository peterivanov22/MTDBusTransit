package com.example.android.mtdbustransit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.mtdbustransit.data.StopsListContract;
import com.example.android.mtdbustransit.data.StopsListDbHelper;
/**
 * Created by peter on 7/29/2015.
 */


public class StopsListAdapter extends CursorAdapter {
    public StopsListAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }

    private StopsListDbHelper mOpenHelper;


    private String convertCursorRowToUXFormat(Cursor cursor){
        int stop_id = cursor.getColumnIndex(StopsListContract.StopsListEntry.COLUMN_STOP_ID);
        int stop_name = cursor.getColumnIndex(StopsListContract.StopsListEntry.COLUMN_STOP_NAME);

        return cursor.getString(stop_name)+" "+ cursor.getString(stop_id);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_stop, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView) view;
        tv.setText(convertCursorRowToUXFormat(cursor));
    }

    public Cursor getListCursor(CharSequence constraint) {

        SQLiteQueryBuilder db = new SQLiteQueryBuilder();
        db.setTables(StopsListContract.StopsListEntry.TABLE_NAME);


        if (constraint == null || constraint.length() == 0) {
            // Return the full list
            return db.query(mOpenHelper.getWritableDatabase(), null,
                    null, null, null, null,
                    StopsListContract.StopsListEntry.COLUMN_STOP_NAME + " ASC");
        } else {

            String value = "%" + constraint.toString() + "%";


            return db.query(mOpenHelper.getWritableDatabase(), null,
                    StopsListContract.StopsListEntry.COLUMN_STOP_NAME + " like ? ", new String[]{value}, null, null,
                    StopsListContract.StopsListEntry.COLUMN_STOP_NAME + " ASC");

        }
    }
}


