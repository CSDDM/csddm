package com.example.csddm.operatedb;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by dell on 2017/2/16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private final static String TAG= "SQLiteHelper";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG,"SQLitehelper onCreate!");
        final String CREATE_USER_TABLE = "create table user ("
                +"account varchar(50) primary key NOT NULL,"
                +"name varchar(50) NOT NULL,"
                +"password varchar(50) NOT NULL)";
        final String CREATE_SONG_TABLE = "create table song ("
                +"songid varchar(8) primary key NOT NULL";
        final String CREATE_SINGER_TABLE = "";
        final String CREATE_LISTENRECORD_TABLE = "";

        try{
            db.execSQL(CREATE_USER_TABLE);
            Log.e(TAG,"createUserTable OK!");
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
