package com.dev.noname.lover.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 12/3/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LoverChatApp.db";
    public static final String DATING_TABLE_NAME = "dating";
    public static final String DATING_TABLE_ID = "id";
    public static final String DATING_TABLE_DATE = "date";
    public static final String DATING_COLUMN_TITLE = "title";
    public static final String DATING_COLUMN_ADDRESS = "address";
    public static final String DATING_COLUMN_MESS = "mess";


    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +DATING_TABLE_NAME+
                "(title text primary key, name text,content text)");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS messages");
        onCreate(db);

    }
    public boolean updateDating (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATING_TABLE_ID, name);
        contentValues.put(DATING_COLUMN_TITLE, phone);
        contentValues.put(DATING_TABLE_DATE, email);
        contentValues.put(DATING_COLUMN_ADDRESS, street);
        contentValues.put(DATING_COLUMN_MESS, place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean createDating (String title, String date, String mess, String id,String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATING_TABLE_ID, id);
        contentValues.put(DATING_COLUMN_TITLE, title);
        contentValues.put(DATING_TABLE_DATE, date);
        contentValues.put(DATING_COLUMN_ADDRESS, address);
        contentValues.put(DATING_COLUMN_MESS, mess);
        db.insert(DATING_TABLE_NAME, null, contentValues);
        return true;
    }

}
