package com.example.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class RemindersDbAdapter {
    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;
    //used for logging
    private static final String TAG = "RemindersDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_CONTENT + " TEXT, " +
                    COL_IMPORTANT + " INTEGER );";


    public RemindersDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }


    //TODO implement the function createReminder() which take the name as the content of the reminder and boolean important...note that the id will be created for you automatically
    public void createReminder(String name, boolean important) {
        open();
        ContentValues contentval = new ContentValues();
        contentval.put(COL_CONTENT,name);
        contentval.put(COL_IMPORTANT,important);
        Long success= mDb.insert(TABLE_NAME,null,contentval);
        close();
        if(success==-1)
        {
            System.out.println("BAAAAAD!!");
        }
    }
    //TODO overloaded to take a reminder
    public long createReminder(Reminder reminder) {
        open();
        ContentValues contentval = new ContentValues();
        contentval.put(COL_CONTENT,reminder.getContent());
        contentval.put(COL_IMPORTANT,reminder.getImportant());
        Long success= mDb.insert(TABLE_NAME,null,contentval);
        if(success==-1)
        {
            System.out.println("BAAAAAD!!");
        }
        close();
        return 0;
    }

    //TODO implement the function fetchReminderById() to get a certain reminder given its id
    public  Reminder fetchReminderById(int id) {
        System.out.println(String.valueOf(id));
        open();
        //Select * from Materials where Title=title AND Course_Code = CourseCode
        String query="Select * from "+TABLE_NAME +" where "+COL_ID +" = ? ";
        Cursor reminderCursor= mDb.rawQuery(query,  new String[] {String.valueOf(id)});
        reminderCursor.moveToFirst();
        String content=reminderCursor.getString(reminderCursor.getColumnIndex(COL_CONTENT));
        int important=reminderCursor.getInt(reminderCursor.getColumnIndex(COL_IMPORTANT));
        Reminder reminder=new Reminder(id,content,important);
        return reminder;
    }


    //TODO implement the function fetchAllReminders() which get all reminders
    public Cursor fetchAllReminders() {
        open();
        String query="Select * from "+TABLE_NAME;
        Cursor cu= mDb.rawQuery(query, null);
        return cu;
    }

    //TODO implement the function updateReminder() to update a certain reminder
    public void updateReminder(Reminder reminder) {
        open();
        ContentValues contentval = new ContentValues();
        contentval.put(COL_CONTENT,reminder.getContent());
        contentval.put(COL_IMPORTANT,reminder.getImportant());
        String whereclause = COL_ID+ " =?";
        String[] arguments = {String.valueOf(reminder.getId())};
        int NumberOfRowAffected= mDb.update(TABLE_NAME, contentval, whereclause, arguments);
    }
    //TODO implement the function deleteReminderById() to delete a certain reminder given its id
    public void deleteReminderById(int nId) {
        open();
        String whereclause = COL_ID+ " =?";
        String[] arguments = {String.valueOf(nId)};
        mDb.delete(TABLE_NAME,whereclause,arguments);
    }

    //TODO implement the function deleteAllReminders() to delete all reminders
    public void deleteAllReminders() {
        open();
        mDb.delete(TABLE_NAME,null,null);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
