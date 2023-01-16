package com.cornez.shippingcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "User.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(hei TEXT primary key, wei TEXT, sta TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }
    public Boolean insertuserdata(String hei,String wei,String sta)
    {
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //contentValues.put("name", name);
        //contentValues.put("sexual", sexual);
        contentValues.put("hei", hei);
        contentValues.put("wei", wei);
        //contentValues.put("age", age);
        contentValues.put("sta", sta);
        long result=DB.insert("Userdetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateuserdata( String hei , String wei,String sta)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("sexual", sexual);
        contentValues.put("hei", hei);
        contentValues.put("wei", wei);
        //contentValues.put("age", age);
        contentValues.put("sta", sta);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{hei});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{hei});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }/**/
    }
    public Boolean deletedata (String hei)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where hei = ?", new String[]{hei});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "hei=?", new String[]{hei});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }
}