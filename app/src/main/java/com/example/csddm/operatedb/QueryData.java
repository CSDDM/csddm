package com.example.csddm.operatedb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.csddm.LoginActivity;
import com.example.csddm.entity.ListenRecord;
import com.example.csddm.entity.Song;
import com.example.csddm.menu.MenuActivity;
import com.example.csddm.web.GetListenRecordWeb;
import com.example.csddm.web.LoginWeb;

import java.util.ArrayList;

/**
 * Created by dell on 2017/2/25.
 */

public class QueryData {

   /* public static ArrayList<ListenRecord> getListenRecordByAccount(String useraccount, SQLiteHelper dbHelper) {
        ArrayList<ListenRecord> record = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String args[] = {useraccount};
        ContentValues cv = new ContentValues();
        cv.put("account", useraccount);
        Cursor c = db.rawQuery("SELECT * FROM listen_record WHERE account=?", args);
        if (c.getCount() != 0) {
            //do soemthing
            while (c.moveToNext()) {
                double time = (double) (c.getInt(c.getColumnIndex("time")));
                Song song = new Song();
                song.setSongid(c.getString(c.getColumnIndex("songid")));
                ListenRecord lr = new ListenRecord();
                lr.setTime(time);
                lr.setSong(song);
                record.add(lr);
            }
        }
        for (ListenRecord lr : record) {
            String args2[] = {lr.getSong().getSongid()};
            ContentValues cv2 = new ContentValues();
            cv2.put("songid", lr.getSong().getSongid());
            Cursor c2 = db.rawQuery("SELECT * FROM song WHERE songid=?", args2);
            if (c2.getCount() != 0) {
                //do soemthing
                while (c2.moveToNext()) {
                    String name = c2.getString(c2.getColumnIndex("name"));
                    double time = (double) (c2.getInt(c2.getColumnIndex("time")));
                    String style = c2.getString(c2.getColumnIndex("style"));
                    String label = c2.getString(c2.getColumnIndex("label"));
                    Song song = lr.getSong();
                    song.setSongname(name);
                    song.setStyle(style);
                    song.setLabel(label);
                    song.setTime(time);
                }
            }
            c2.close();
            cv2.clear();
        }
        db.close();
        return record;
    }*/
   private ArrayList<ListenRecord> record = new ArrayList<>();
    private String useraccount;
   public  ArrayList<ListenRecord> getListenRecordByAccount(String useraccount, SQLiteHelper dbHelper) {
       this.useraccount = useraccount;
       // 检查网络
       // 创建子线程，分别进行Get和Post传输
       MyQueryRecordThread thread = new MyQueryRecordThread();
       thread.start();
       while(!thread.getIsDone()){

       }
       return record;
   }

    // 子线程接收数据，主线程修改数据
    public class MyQueryRecordThread extends Thread {
        private boolean isDone = false;
        @Override
        public void run() {
            record = GetListenRecordWeb.getListenRecord(useraccount);
            isDone=true;
        }
        public boolean getIsDone(){
            return isDone;
        }
    }

    public static double[][] getS1CharacterData() {
        double[][] character = {{0, 15, 20, 0, 0, 0, 10, 0, 0, 25, 30, 0, 0, 0},
                {0, 5, 20, 0, 0, 0, 0, 5, 0, 30, 40, 0, 0, 0},
                {15, 15, 0, 15, 15, 0, 10, 0, 0, 15, 0, 10, 0, 5},
                {20, 0, 0, 25, 15, 0, 5, 0, 5, 0, 0, 15, 0, 15},
                {20, 0, 0, 10, 15, 0, 10, 0, 10, 0, 0, 25, 0, 10},
                {0, 20, 25, 0, 10, 5, 15, 0, 5, 0, 10, 0, 10, 0},
                {10, 10, 20, 0, 0, 30, 5, 0, 5, 0, 0, 0, 15, 5},
                {20, 0, 10, 0, 25, 0, 20, 0, 10, 0, 0, 5, 0, 10},
                {5, 5, 10, 0, 0, 30, 5, 5, 25, 0, 0, 0, 5, 10},
                {15, 0, 0, 0, 25, 0, 30, 0, 10, 0, 0, 10, 0, 10},
                {0, 15, 25, 0, 0, 0, 0, 10, 20, 0, 0, 0, 30, 0},
                {0, 20, 15, 5, 10, 0, 15, 0, 10, 0, 0, 10, 0, 15},
                {5, 15, 10, 0, 15, 0, 25, 0, 10, 0, 0, 0, 5, 15},
                {5, 25, 20, 0, 5, 0, 5, 0, 0, 20, 0, 0, 15, 5},
                {10, 10, 0, 10, 15, 0, 10, 10, 0, 10, 15, 0, 10, 0},
                {0, 20, 10, 0, 10, 0, 5, 10, 0, 10, 30, 0, 5, 0},
                {10, 0, 0, 0, 20, 5, 20, 0, 10, 0, 0, 10, 0, 25}};
        return character;
    }

    public static double[][] getS2YoungCharacterData() {
        double[][] character = {{0, 20, 20, 0, 0, 20, 10, 0, 10, 0, 15, 0, 5, 0},
                {30, 0, 0, 10, 15, 0, 20, 0, 5, 0, 0, 5, 0, 15},
                {25, 0, 5, 5, 5, 10, 15, 5, 5, 5, 5, 5, 10, 0},
                {0, 20, 25, 0, 0, 5, 0, 10, 0, 10, 20, 0, 10, 0},
                {15, 0, 0, 15, 35, 0, 10, 0, 0, 0, 0, 15, 0, 10}};
        return character;
    }

    public static double[][] getS2MiddleCharacterData() {
        double[][] character = {{15, 0, 0, 15, 35, 0, 10, 0, 0, 0, 0, 15, 0, 10},
                {10, 0, 0, 0, 10, 30, 20, 0, 15, 0, 0, 5, 0, 10},
                {0, 15, 20, 0, 5, 5, 10, 5, 0, 15, 15, 0, 10, 0},
                {5, 5, 15, 0, 10, 20, 25, 0, 10, 0, 0, 0, 0, 10},
                {0, 5, 20, 0, 5, 15, 0, 0, 10, 0, 15, 0, 30, 0},
                {5, 5, 10, 0, 5, 10, 25, 0, 10, 0, 0, 0, 30, 0},
                {15, 0, 0, 15, 20, 0, 10, 0, 5, 0, 0, 10, 20, 5},
                {15, 0, 5, 5, 15, 10, 5, 0, 15, 0, 0, 0, 25, 5},
                {0, 5, 30, 0, 5, 35, 0, 0, 10, 0, 0, 0, 10, 5},
                {0, 10, 20, 0, 5, 20, 15, 0, 15, 0, 0, 0, 15, 0},
                {15, 0, 0, 30, 15, 0, 25, 0, 0, 0, 0, 10, 0, 5},
                {0, 20, 20, 0, 0, 25, 0, 5, 0, 10, 15, 0, 5, 0},
                {5, 0, 0, 20, 25, 0, 10, 0, 5, 0, 0, 30, 0, 5},
                {10, 0, 0, 35, 20, 0, 10, 0, 0, 0, 0, 25, 0, 0},
                {5, 0, 0, 10, 30, 0, 15, 0, 5, 0, 0, 15, 0, 20}};
        return character;
    }

    public static double[][] getS2OldCharacterData() {
        double[][] character = {{0, 5, 20, 0, 5, 15, 0, 0, 10, 0, 15, 0, 30, 0},
                {5, 5, 10, 0, 5, 10, 25, 0, 10, 0, 0, 0, 30, 0},
                {15, 0, 0, 15, 20, 0, 10, 0, 5, 0, 0, 10, 20, 5},
                {15, 0, 5, 5, 15, 10, 5, 0, 15, 0, 0, 0, 25, 5},
                {0, 5, 30, 0, 5, 35, 0, 0, 10, 0, 0, 0, 10, 5},
                {0, 10, 20, 0, 5, 20, 15, 0, 15, 0, 0, 0, 15, 0},
                {15, 0, 0, 30, 15, 0, 25, 0, 0, 0, 0, 10, 0, 5},
                {0, 20, 20, 0, 0, 25, 0, 5, 0, 10, 15, 0, 5, 0},
                {5, 0, 0, 20, 25, 0, 10, 0, 5, 0, 0, 30, 0, 5},
                {10, 0, 0, 35, 20, 0, 10, 0, 0, 0, 0, 25, 0, 0},
                {5, 0, 0, 10, 30, 0, 15, 0, 5, 0, 0, 15, 0, 20}};
        return character;
    }

    public static double[][] getS5CharacterData() {
        double[][] character = {{10, 0, 2, 0, 30, 2, 40, 0, 1, 0, 0, 10, 2, 3},
                {0, 30, 40, 1, 0, 2, 2, 2, 0, 10, 10, 1, 1, 1},
                {10, 5, 45, 2, 5, 2, 5, 2, 5, 2, 2, 5, 5, 5},
                {2, 2, 2, 15, 15, 2, 40, 5, 5, 0, 1, 5, 3, 3},
                {1, 0, 5, 20, 15, 0, 4, 30, 5, 10, 2, 8, 0, 0},
                {0, 20, 40, 5, 2, 0, 5, 0, 2, 14, 2, 10, 0, 0},
                {8, 2, 2, 15, 25, 0, 20, 0, 3, 0, 0, 0, 0, 25},
                {5, 15, 10, 0, 20, 2, 35, 0, 1, 0, 0, 10, 2, 0},
                {8, 2, 12, 0, 30, 2, 35, 0, 1, 0, 0, 5, 2, 3},
                {0, 25, 45, 1, 0, 2, 5, 1, 0, 8, 10, 1, 1, 1},
                {0, 25, 40, 5, 0, 0, 5, 0, 0, 14, 4, 7, 0, 0},
                {10, 0, 1, 16, 25, 0, 20, 0, 3, 0, 0, 0, 5, 20},
                {0, 15, 30, 5, 2, 0, 11, 0, 2, 18, 2, 10, 5, 0},
                {13, 2, 40, 2, 8, 2, 7, 2, 5, 2, 2, 5, 5, 5},
                {0, 30, 40, 1, 0, 2, 2, 2, 0, 15, 15, 3, 0, 0},
                {12, 0, 2, 0, 30, 0, 35, 0, 5, 0, 0, 12, 1, 3},
                {0, 5, 0, 12, 18, 2, 35, 0, 10, 0, 5, 7, 3, 3},
                {10, 0, 0, 17, 25, 0, 20, 0, 3, 0, 0, 0, 0, 25},
                {0, 25, 35, 0, 0, 10, 0, 7, 0, 10, 10, 1, 1, 1},
                {0, 10, 0, 25, 15, 0, 0, 25, 5, 10, 2, 8, 0, 0}};
        return character;
    }

    public static double[][] getS6CharacterData() {
        double[][] character = {{0, 20, 5, 0, 0, 0, 0, 25, 0, 15, 5, 0, 30, 0},
                {0, 40, 0, 0, 0, 0, 0, 15, 0, 15, 10, 0, 20, 0},
                {0, 25, 5, 0, 0, 0, 0, 30, 0, 20, 10, 0, 10, 0},
                {2, 15, 10, 2, 2, 5, 5, 15, 2, 15, 20, 0, 7, 0},
                {5, 5, 10, 5, 10, 15, 5, 5, 8, 5, 7, 10, 5, 5},
                {5, 5, 25, 0, 5, 20, 5, 3, 7, 2, 8, 5, 5, 5},
                {5, 10, 20, 0, 5, 20, 5, 10, 5, 5, 10, 0, 5, 0},
                {10, 0, 0, 20, 10, 0, 5, 5, 0, 15, 0, 30, 5, 0},
                {10, 0, 0, 50, 10, 0, 2, 2, 0, 20, 0, 6, 0, 0},
                {10, 10, 10, 10, 10, 0, 5, 5, 0, 10, 0, 20, 5, 5},
                {10, 10, 25, 0, 5, 15, 8, 2, 8, 2, 5, 10, 0, 0},
                {15, 0, 0, 20, 10, 0, 5, 0, 5, 5, 0, 15, 5, 20},
                {15, 0, 0, 45, 10, 0, 2, 8, 0, 10, 0, 10, 0, 0},
                {15, 0, 5, 10, 5, 0, 5, 5, 0, 20, 5, 20, 0, 10},
                {15, 0, 5, 10, 15, 0, 15, 0, 10, 0, 0, 5, 0, 25},
                {15, 5, 0, 25, 10, 0, 5, 5, 0, 20, 0, 15, 0, 0},
                {15, 5, 5, 15, 10, 0, 5, 10, 5, 10, 0, 20, 0, 0},
                {20, 0, 0, 30, 20, 0, 10, 2, 2, 3, 0, 3, 0, 10},
                {20, 5, 5, 5, 15, 5, 15, 0, 10, 0, 5, 5, 0, 10},
                {25, 0, 0, 15, 15, 0, 15, 0, 10, 5, 0, 0, 0, 15}};
        return character;
    }

    public static double[][] getS7CharacterData() {
        double[][] character = {{0, 5, 5, 0, 0, 0, 0, 20, 0, 30, 10, 0, 30, 0},
                {0, 15, 10, 0, 0, 0, 0, 40, 0, 15, 5, 0, 15, 0},
                {0, 15, 15, 0, 0, 0, 0, 40, 0, 10, 5, 0, 15, 0},
                {0, 25, 5, 0, 0, 0, 0, 30, 0, 20, 10, 0, 10, 0},
                {0, 30, 10, 0, 0, 0, 0, 20, 0, 10, 20, 0, 10, 0},
                {0, 30, 20, 0, 0, 20, 0, 20, 0, 10, 0, 0, 0, 0},
                {0, 40, 10, 0, 0, 0, 0, 20, 0, 10, 20, 0, 0, 0},
                {5, 10, 5, 5, 5, 0, 5, 10, 0, 30, 10, 0, 10, 5},
                {5, 15, 10, 0, 0, 0, 0, 25, 0, 35, 0, 0, 10, 0},
                {5, 15, 20, 0, 0, 10, 0, 10, 0, 10, 10, 0, 20, 0},
                {10, 0, 5, 15, 10, 0, 10, 10, 5, 5, 0, 20, 0, 10},
                {10, 10, 0, 20, 0, 0, 0, 15, 0, 15, 0, 30, 0, 0},
                {10, 15, 10, 10, 10, 0, 5, 10, 0, 10, 10, 0, 10, 0},
                {10, 15, 15, 0, 0, 10, 5, 15, 0, 10, 10, 0, 10, 0},
                {15, 5, 0, 25, 15, 0, 5, 5, 5, 5, 0, 10, 0, 10},
                {15, 5, 5, 5, 5, 25, 20, 0, 10, 0, 5, 0, 0, 5},
                {20, 0, 0, 50, 0, 0, 0, 0, 0, 10, 0, 20, 0, 0},
                {25, 0, 5, 15, 15, 5, 5, 5, 10, 5, 0, 10, 0, 0},
                {25, 0, 10, 15, 5, 0, 0, 5, 0, 10, 0, 30, 0, 0},
                {30, 0, 0, 10, 20, 0, 10, 0, 10, 0, 0, 10, 0, 10}};
        return character;
    }

    public static double[][] getS8CharacterData() {
        double[][] character = {{20, 0, 5, 5, 5, 0, 30, 0, 10, 0, 0, 5, 10, 10},
                {30, 20, 20, 0, 0, 0, 10, 0, 5, 0, 5, 0, 0, 10},
                {25, 0, 0, 10, 10, 0, 10, 0, 5, 0, 0, 10, 10, 20},
                {0, 20, 20, 10, 0, 0, 0, 20, 0, 10, 10, 0, 10, 0},
                {5, 10, 5, 35, 5, 0, 5, 5, 5, 5, 0, 10, 10, 0},
                {10, 0, 0, 20, 20, 0, 10, 0, 5, 0, 0, 25, 0, 10},
                {20, 0, 0, 0, 15, 0, 30, 0, 10, 0, 0, 5, 10, 10},
                {0, 20, 5, 35, 0, 0, 0, 10, 0, 10, 10, 0, 10, 0},
                {30, 0, 5, 10, 10, 0, 15, 0, 10, 0, 0, 10, 0, 10},
                {10, 0, 0, 20, 30, 0, 0, 0, 10, 0, 0, 30, 0, 0},
                {40, 0, 0, 0, 40, 0, 10, 0, 5, 0, 0, 0, 0, 5},
                {40, 0, 0, 0, 40, 0, 10, 0, 5, 0, 0, 0, 0, 5},
                {10, 0, 0, 20, 15, 0, 10, 0, 20, 0, 0, 5, 0, 20},
                {30, 0, 0, 10, 20, 0, 20, 0, 10, 0, 0, 0, 0, 10},
                {5, 0, 20, 0, 5, 20, 40, 0, 0, 0, 0, 0, 10, 0},
                {0, 20, 20, 0, 0, 0, 0, 0, 0, 30, 30, 0, 0, 0},
                {20, 0, 0, 0, 30, 0, 20, 0, 5, 0, 0, 10, 10, 5},
                {0, 40, 20, 0, 0, 0, 0, 20, 0, 10, 0, 0, 10, 0}};
        return character;
    }

    public static double[][] getS9CharacterData() {
        double[][] character = {{0, 20, 20, 0, 0, 0, 0, 0, 0, 20, 20, 0, 20, 0},
                {0, 20, 15, 5, 10, 0, 20, 0, 10, 0, 0, 0, 20, 0},
                {0, 20, 20, 0, 0, 5, 25, 0, 10, 0, 10, 0, 0, 10},
                {20, 0, 10, 0, 10, 0, 20, 0, 20, 0, 0, 0, 0, 20},
                {0, 20, 20, 0, 0, 0, 0, 10, 0, 20, 20, 0, 10, 0},
                {20, 0, 10, 0, 10, 0, 20, 0, 20, 0, 0, 0, 0, 20},
                {0, 20, 0, 0, 0, 0, 0, 50, 0, 10, 0, 0, 20, 0},
                {10, 0, 0, 0, 10, 0, 20, 0, 40, 0, 0, 0, 0, 20},
                {0, 20, 20, 0, 0, 0, 0, 0, 0, 30, 30, 0, 0, 0},
                {0, 10, 50, 0, 10, 0, 10, 0, 0, 0, 0, 10, 10, 0}};
        return character;
    }

    public static double[][] getS10CharacterData() {
        double[][] character = {{30, 0, 0, 0, 20, 10, 30, 0, 10, 0, 0, 0, 0, 0},
                {0, 30, 0, 20, 0, 0, 0, 10, 0, 10, 10, 0, 20, 0},
                {0, 10, 40, 0, 0, 40, 0, 0, 0, 5, 5, 0, 0, 0},
                {0, 0, 0, 0, 20, 0, 0, 0, 20, 0, 10, 40, 10, 0},
                {10, 0, 20, 0, 0, 40, 10, 0, 0, 0, 0, 0, 20, 0},
                {10, 0, 0, 10, 20, 0, 0, 0, 20, 0, 0, 40, 0, 0},
                {10, 0, 30, 0, 0, 0, 40, 0, 5, 0, 0, 0, 10, 5},
                {40, 0, 0, 0, 20, 0, 20, 0, 10, 0, 0, 0, 10, 0},
                {30, 0, 0, 20, 30, 0, 10, 0, 10, 0, 0, 0, 0, 0},
                {10, 0, 15, 0, 15, 30, 20, 0, 10, 0, 0, 0, 0, 0}};
        return character;
    }

    public static double[][] getS11CharacterData() {
        double[][] character = {{0, 20, 20, 0, 0, 0, 0, 0, 0, 20, 20, 0, 20, 0},
                {0, 20, 15, 5, 10, 0, 20, 0, 10, 0, 0, 0, 20, 0},
                {0, 20, 20, 0, 0, 5, 25, 0, 10, 0, 10, 0, 0, 10},
                {20, 0, 10, 0, 10, 0, 20, 0, 20, 0, 0, 0, 0, 20},
                {0, 20, 20, 0, 0, 0, 0, 10, 0, 20, 20, 0, 10, 0},
                {20, 0, 10, 0, 10, 0, 20, 0, 20, 0, 0, 0, 0, 20},
                {0, 20, 0, 0, 0, 0, 0, 50, 0, 10, 0, 0, 20, 0},
                {10, 0, 0, 0, 10, 0, 20, 0, 40, 0, 0, 0, 0, 20},
                {0, 20, 20, 0, 0, 0, 0, 0, 0, 30, 30, 0, 0, 0},
                {0, 10, 50, 0, 10, 0, 10, 0, 0, 0, 0, 10, 10, 0}};
        return character;
    }

    public static double[][] getS12YoungCharacterData() {
        double[][] character = {
                {10, 5, 10, 0, 0, 0, 20, 10, 5, 0, 5, 0, 0, 35},
                {10, 0, 5, 30, 25, 0, 10, 0, 10, 0, 0, 5, 0, 5},
                {0, 20, 10, 5, 5, 0, 30, 0, 10, 0, 10, 0, 0, 10},
                {10, 10, 30, 0, 5, 10, 20, 0, 10, 0, 0, 0, 0, 5},
                {10, 10, 10, 0, 0, 0, 10, 10, 0, 30, 20, 0, 0, 0},
                {0, 20, 0, 10, 0, 0, 0, 20, 0, 30, 20, 0, 0, 0},
                {20, 0, 0, 10, 10, 0, 20, 0, 20, 0, 0, 20, 0, 0}};
        return character;
    }

    public static double[][] getS12MiddleCharacterData() {
        double[][] character = {
                {5, 10, 20, 0, 0, 20, 20, 0, 20, 0, 0, 0, 0, 5},
                {10, 20, 20, 0, 0, 0, 10, 0, 0, 10, 30, 0, 0, 0},
                {0, 20, 30, 0, 5, 0, 10, 0, 0, 5, 20, 0, 10, 0},
                {0, 30, 5, 5, 0, 0, 0, 0, 0, 30, 20, 0, 10, 0},
                {20, 0, 5, 10, 10, 20, 10, 0, 20, 0, 0, 0, 0, 5},
                {10, 10, 10, 0, 0, 15, 10, 0, 10, 0, 5, 0, 15, 15}};
        return character;
    }

    public static double[][] getS12OldCharacterData() {
        double[][] character = {
                {0, 20, 20, 0, 10, 0, 5, 5, 0, 5, 35, 0, 0, 0},
                {0, 20, 20, 0, 0, 0, 10, 0, 20, 0, 10, 0, 0, 20},
                {0, 20, 20, 0, 0, 0, 0, 10, 0, 20, 20, 0, 10, 0},
                {10, 0, 10, 10, 20, 10, 10, 0, 20, 0, 0, 0, 0, 10}};
        return character;
    }

    public static double[][] getS13YoungCharacterData() {
        double[][] character = {
                {25, 5, 0, 10, 20, 0, 20, 0, 10, 0, 0, 5, 0, 5},
                {25, 0, 0, 10, 25, 0, 10, 0, 20, 0, 10, 0, 0, 0},
                {5, 20, 15, 0, 0, 5, 10, 15, 0, 10, 10, 0, 10, 0},
                {0, 25, 15, 0, 0, 25, 0, 5, 5, 0, 15, 0, 10, 0},
                {20, 0, 0, 5, 10, 0, 15, 0, 15, 0, 0, 20, 0, 15},
                {0, 25, 10, 0, 0, 25, 0, 10, 0, 0, 10, 0, 20, 0}};
        return character;
    }

    public static double[][] getS13MiddleCharacterData() {
        double[][] character = {
                {5, 20, 10, 0, 5, 0, 5, 10, 0, 20, 15, 0, 10, 0},
                {25, 0, 0, 10, 10, 10, 10, 0, 10, 0, 0, 25, 0, 0},
                {30, 0, 0, 10, 25, 10, 10, 0, 10, 0, 0, 5, 0, 0},
                {20, 5, 10, 0, 0, 25, 5, 5, 20, 0, 0, 0, 10, 0},
                {20, 5, 5, 10, 25, 0, 5, 0, 5, 0, 0, 15, 0, 10}};
        return character;
    }

    public static double[][] getS13OldCharacterData() {
        double[][] character = {
                {5, 20, 15, 0, 0, 15, 0, 10, 0, 10, 25, 0, 0, 0},
                {20, 0, 20, 0, 5, 25, 10, 0, 0, 0, 10, 0, 10, 0},
                {0, 25, 15, 0, 0, 25, 0, 5, 0, 10, 20, 0, 0, 0}};
        return character;
    }
}
