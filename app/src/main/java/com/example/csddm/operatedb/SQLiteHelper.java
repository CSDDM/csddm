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
                +"songid varchar(8) primary key NOT NULL,"
                +"name varchar(50) NOT NULL,"
                +"pubdate varchar(50),"
                +"style varchar(30),"
                +"label varchar(30),"
                +"album varchar(50),"
                +"language varchar(50),"
                +"song varchar(100),"
                +"lyric varchar(100),"
                +"time int,"
                +"singerid varchar(8) REFERENCES singer(singerid) )";//外键
        final String CREATE_SINGER_TABLE = "create table singer ("
                +"singerid varchar(8) primary key NOT NULL,"
                +"name varchar(30),"
                +"gender varchar(10),"
                +"birth varchar(50),"
                +"nation varchar(30) )";
        final String CREATE_LISTENRECORD_TABLE = "create table listen_record ("
                +"recordid vrachar(8) primary key NOT NULL,"
                +"account varchar(50) NOT NULL REFERENCES user(account),"
                +"songid varchar(8) NOT NULL REFERENCES song(songid),"
                +"time int NOT NULL)";
        try{
            db.execSQL(CREATE_USER_TABLE);
            Log.e(TAG,"createUserTable OK!");
            db.execSQL(CREATE_SINGER_TABLE);
            Log.e(TAG,"createSingerTable OK!");
            db.execSQL(CREATE_SONG_TABLE);
            Log.e(TAG,"createSongTable OK!");
            db.execSQL(CREATE_LISTENRECORD_TABLE);
            Log.e(TAG,"createListenRecordTable OK!");
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    private void tmp(SQLiteDatabase db){


        Log.e(TAG,"SQLitehelper onCreate!");

        try{
            db.execSQL("drop table user");
            Log.e(TAG,"dropUserTable OK!");
            db.execSQL("drop table singer");
            Log.e(TAG,"dropSingerTable OK!");
            db.execSQL("drop table song");
            Log.e(TAG,"dropSongTable OK!");
            db.execSQL("drop table listen_record");
            Log.e(TAG,"dropListenRecordTable OK!");
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
