package com.firebase.dailyexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eunoiatechnologies on 15/09/16.
 */

public class ExpensesDAO extends AbstaractDAO implements Constants {
    public ExpensesDAO(Context context) {
        super(context);
    }

    private String TAG = "ExpensesDAO.java";








    public List<Map> getCurrentDateLessTenAndGreaterTen()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date plusDate = calendar.getTime();
        Log.i(TAG,"plus time"+plusDate);
        String plusD = dateFormat.format(plusDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, -15);
        Date minusDate = calendar1.getTime();
        Log.i(TAG,"minus time"+minusDate);
        String minusD = dateFormat.format(minusDate);

        Log.i(TAG,"ddd"+plusD.split(" ")[0]);
   //     String sql = "SELECT "+COLUMN_DATE+" FROM "+TABLE_NAME_DATES+ " WHERE Datetime('"+COLUMN_DATE+"') <= Datetime('"+plusD.split(" ")[0]+"')";
        String dateress= "2016/04/20";
        String sql = "SELECT "+COLUMN_DATE+" FROM "+TABLE_NAME_DATES+ " WHERE "+COLUMN_DATE+" <= "+ "'"+plusD.split(" ")[0]+"' and "+COLUMN_DATE+" >= "+ "'"+minusD.split(" ")[0]+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        List<Map> lmap = new ArrayList<>();
        if(cursor!=null && cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    Map map = new HashMap();
                    map.put("date",cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                    Log.i(TAG,"datesss"+cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                    lmap.add(map);
                }while (cursor.moveToNext());
                return lmap;
            }
        }


        return null;
    }





}