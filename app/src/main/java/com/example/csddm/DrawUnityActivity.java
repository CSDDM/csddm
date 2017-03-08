package com.example.csddm;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.csddm.menu.DummyContent;
import com.example.csddm.menu.MenuActivity;
import com.example.csddm.web.GetSongWeb;
import com.example.csddm.web.IPWeb;
import com.unity3d.player.UnityPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell on 2017/3/2.
 */

public class DrawUnityActivity extends UnityPlayerActivity {
    private final String ROOTPATH = "http://" + IPWeb.IP + "/SYCserver/";
    public static final String TAG_STOPPOSITION= "stopposition";
    public static final String TAG_SONGPATH= "songpath";
    private String useraccount;
    private String username;
    private int musicindex;
    private int levelnumber;
    private int startposition;
    private int stopposition;
     //背景音乐
    private MediaPlayer mp3;
    private String songpath;

    private LinearLayout u3dLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawunity);
        Intent intent = getIntent();
        musicindex = intent.getIntExtra(ListeningMusicActivity.TAG_MUSICINDEX, 0);
        startposition = intent.getIntExtra(TAG_STOPPOSITION, 0);
        useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
        username = intent.getStringExtra(MenuActivity.TAG_USERNAME);
        levelnumber = musicindex+1;//可以替换
        songpath = intent.getStringExtra(TAG_SONGPATH);
        Log.i("musicindex",""+musicindex);
        System.out.println("levelnumber==========================  "+levelnumber);

        mp3 = new MediaPlayer();    //创建一个MediaPlayer对象
        Uri uri = Uri.parse(songpath);
        mp3 = MediaPlayer.create(this, uri);
        mp3.setLooping(true);
        startMusic();
        mUnityPlayer = new UnityPlayer(this);
        Log.i("mUnityPlayerChildCount",""+mUnityPlayer.getChildCount());
        u3dLayout = (LinearLayout) findViewById(R.id.u3d_layout);
        u3dLayout.addView(mUnityPlayer.getView());
        mUnityPlayer.requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("key", "key");
            mp3.release();
            mUnityPlayer.quit();
            finish();
//            if(mp3!=null){
//                stopposition = mp3.getCurrentPosition();
//                mp3.release();
//            }
            onDestroy();
//            Intent intent = new Intent();
//            intent.putExtra(MenuActivity.TAG_USERACCOUNT,useraccount);
//            intent.putExtra(MenuActivity.TAG_USERNAME,username);
//            intent.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, musicindex);
//            intent.putExtra(ListeningMusicActivity.TAG_STARTPOSITION, stopposition);
//            intent.setAction("finishUnity");
//            sendBroadcast(intent);
//            Log.e("DrawUnity_Position",""+stopposition);
            //            finish();
//        startActivity(intent);
//            Intent intent = new Intent(".ListeningMusicActivity");
//            intent.putExtra(MenuActivity.TAG_USERACCOUNT,useraccount);
//            intent.putExtra(MenuActivity.TAG_USERNAME,username);
//            intent.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, musicindex);
//            intent.putExtra(ListeningMusicActivity.TAG_STARTPOSITION, stopposition);
//            startActivity(intent);
        }
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }

    public void startMusic() {
        try {
            if (mp3 != null) {
                mp3.stop();
            }
            mp3.prepare();
            //进入到准备状态
            mp3.seekTo(startposition);
            mp3.start();
            //开始播放
            //state.setText("Playing");  //改变输出信息为“Playing”，下同
        } catch (Exception e) {
            //state.setText(e.toString());//以字符串的形式输出异常
            e.printStackTrace();  //在控制台（control）上打印出异常
        }
    }

    public int getLevelNumber(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DrawUnityActivity.this, str,Toast.LENGTH_LONG).show();
            }
        });
        return levelnumber;
    }
    /**
     * 3D调用此方法，用于退出3D
     */
//    public void makePauseUnity() {
//        Log.i("DrawUnity","成功被调用");
//        int position = mp3.getCurrentPosition();
//        mp3.stop();
//        mp3.release();
////        startActivity(intent);
//        Intent intent = new Intent(".ListeningMusicActivity");
//        intent.putExtra(MenuActivity.TAG_USERACCOUNT,useraccount);
//        intent.putExtra(MenuActivity.TAG_USERNAME,username);
//        intent.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, musicindex);
//        intent.putExtra(ListeningMusicActivity.TAG_STARTPOSITION, position);
//        startActivity(intent);
//        mUnityPlayer.quit();
//        finish();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mUnityPlayer != null) {
//                    try {
//                        mUnityPlayer.quit();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.i("DrawUnity","onBackPressed");
//                mp3.stop();
//                mp3.release();
//            }
//        });
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //UnityPlayer.UnitySendMessage("Manager", "Unload", "");
        mUnityPlayer.quit();
    }
    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }
    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
