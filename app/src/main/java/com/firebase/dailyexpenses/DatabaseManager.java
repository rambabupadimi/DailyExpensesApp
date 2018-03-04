package com.firebase.dailyexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eunoiatechnologies on 15/09/16.
 */

public class DatabaseManager extends SQLiteOpenHelper implements Constants {
    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private static String TAG = "DatabaseManager";
    private SQLiteDatabase mDatabase;
    private Context context;
    private AtomicInteger mOpenCounter = new AtomicInteger();

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EXPENSES + " (" + COLUMN_EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_AMOUNT + " INTEGER," + COLUMN_DESCRIPTION + " TEXT, "+FLAG+" TEXT ,"+COLUMN_DATE_TO_STORE+" TEXT ,"+COLUMN_CREATE_TIME+" TEXT ,"+COLUMN_MODIFIED_TIME+" TEXT)";
        db.execSQL(sql);
        String sql1 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME_DATES+ "("+ COLUMN_DATE_ID+ " TEXT, "+COLUMN_DATE+" TEXT )";
        db.execSQL(sql1);
        insertDateDB(db);
        String sql2 =   "CREATE TABLE IF NOT EXISTS "+TABLE_NAME_REGISTER+ " ( "+COLUMN_REGISTER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_NAME+" TEXT, "+COLUMN_PASSWORD+ " TEXT, "+COLUMN_PHONE+" TEXT ,"+COLUMN_CREATE_TIME+" TEXT ,"+COLUMN_MODIFIED_TIME+ " TEXT "+COLUMN_IS_MAIN+ " TEXT "+COLUMN_PARENT_ID+ " INTEGER "+COLUMN_MEMBER_USER_NAME+" TEXT "+COLUMN_READ_ACCESS+" TEXT "+COLUMN_WRITE_ACCESS+" TEXT )";
        db.execSQL(sql2);
        String sql3 =   "CREATE TABLE IF NOT EXISTS "+TABLE_NAME_REGISTER_MEMBER+ " ( "+COLUMN_REGISTER_MEMBER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_REGISTER_MEMBER_NAME+" TEXT, "+COLUMN_REGISTER_MEMBER_USER_NAME+ " TEXT, "+COLUMN_REGISTER_MEMBER_READ_ACCESS+" TEXT, "+COLUMN_REGISTER_MEMBER_WRITE_ACCESS+ " TEXT ,"+COLUMN_CREATE_TIME+" TEXT ,"+COLUMN_MODIFIED_TIME+" TEXT)";
        db.execSQL(sql3);

         //"Database Synchronization Table"
        String sql4 = "CREATE TABLE IF NOT EXISTS "+TABLE_DB_SYNC+" ("+COLUMN_DB_SYNC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_DB_USER_ID+" INTEGER,"+COLUMN_DB_SYNC_UPDATED_TIME+" TEXT)";
        db.execSQL(sql4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if(mOpenCounter.incrementAndGet() == 1) {
            try{
                mDatabase = instance.getWritableDatabase();
            }catch(Exception e){
                Log.e(TAG,""+e);
            }
        }
        if (!mDatabase.isOpen())
        {
            try{
                mDatabase = instance.getWritableDatabase();
            }catch(Exception e){
                Log.e(TAG,""+e);
            }
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0&&mDatabase.isOpen()) {
            try{
                mDatabase.close();
            }catch(Exception e){
                Log.e(TAG,""+e);
            }
        }
        //HLog.i(TAG,"After Closing Database "+mOpenCounter.get());
    }

    public synchronized void deleteDatabase(){
        //HLog.i(TAG,"Before Deleting Database "+mOpenCounter.get());
        try{
            context.deleteDatabase(DATABASE_NAME);
            mOpenCounter.set(0);
        }catch (Exception e) {

        }
        //HLog.i(TAG,"After Deleting Database "+mOpenCounter.get());

    }

    public void insertDateDB(SQLiteDatabase db)
    {


        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate 	= null;
        try {
            startDate = fmt.parse("2018-01-01");
            Date endDate	=	fmt.parse("2019-01-01");
            List<String> dateList = getDaysBetweenDates(startDate,endDate);

            for(int i=0;i<dateList.size();i++)
            {
                String d = dateList.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_DATE,d.split(" ")[0]);
                db.insert(TABLE_NAME_DATES,null,contentValues);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<String> dates = new ArrayList<String>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {

            Date result = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //get current date time with Date()
            Date date = new Date();

            System.out.println(dateFormat.format(result));
            String date1 = dateFormat.format(result);
            dates.add(date1);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

}