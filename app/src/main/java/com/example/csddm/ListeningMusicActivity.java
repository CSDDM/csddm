package com.example.csddm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.csddm.drawface.DrawActivity;
import com.example.csddm.drawface.res.MyResourse;
import com.example.csddm.menu.DummyContent;
import com.example.csddm.menu.MenuActivity;
import com.example.csddm.web.GetSongWeb;
import com.example.csddm.web.IPWeb;
import com.example.csddm.web.SaveListenRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ListeningMusicActivity extends AppCompatActivity {       //声名变量
    public static final String TAG_MUSICINDEX = "musicIndex";
    public static final String TAG_STARTPOSITION = "startposition";
    private Button start = null;
    private Button pause = null;
    private Button last = null;
    private Button next = null;
    private MediaPlayer mp3;
    SeekBar seekBar;
    private Timer mTimer;
    private TimerTask mTimerTask;
    final private ArrayList<String> SONGIDS = DummyContent.SONGIDS;
    private int musicindex;//记录当前音乐在音乐列表中的下标
    private String useraccount;
    private String username;
    private int startposition;
    private final String ROOTPATH = "http://" + IPWeb.IP + "/SYCserver/";
    private String songpath;
    private String lyric;
    private boolean flag = false; //设置标记，false表示正在播放
    private boolean isChanging = false;//互斥变量，防止定时器与seekbar拖动时进度冲突
    private boolean isInCurPage = true;

    ArrayList<HashMap<String, String>> info;//获取网络音乐与歌词资源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_listening_music);
        Intent intent = getIntent();
        musicindex = intent.getIntExtra(TAG_MUSICINDEX, 0);
        startposition = intent.getIntExtra(TAG_STARTPOSITION, 0);
        Log.i("listenmusic_position", "" + startposition);
        useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
        username = intent.getStringExtra(MenuActivity.TAG_USERNAME);
        //取得各按钮组件
        start = (Button) super.findViewById(R.id.play);
        pause = (Button) super.findViewById(R.id.pause);
        last = (Button) findViewById(R.id.last);
        next = (Button) findViewById(R.id.next);
        //滚动条
        seekBar = (SeekBar) findViewById(R.id.seekbar_listenmusic);
        seekBar.setOnSeekBarChangeListener(new MySeekbar());
        //为每个按钮设置单击事件
        start.setOnClickListener(new OnClickListenerStart());
        pause.setOnClickListener(new OnClickListenerPause());
        last.setOnClickListener(new OnClickListenerLast());
        next.setOnClickListener(new OnClickListenerNext());
        //获取当前歌曲与歌词
        this.getSongLyric(SONGIDS.get(musicindex));
        //设置歌词
        TextView textView = (TextView) findViewById(R.id.lyric_listenmusic);
        textView.setText(lyric);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        mp3 = new MediaPlayer();    //创建一个MediaPlayer对象
        mp3.seekTo(startposition);
        Uri uri = Uri.parse(ROOTPATH + songpath);
        mp3 = MediaPlayer.create(this, uri);
        mp3.setLooping(true);
        startMusic();
        //悬浮框用于点击进入歌曲3D场景界面
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_listenmusic);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "歌曲3D场景", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //mp3.pause();
                //flag = true;
                //mTimer.cancel();
                //mTimerTask.cancel();
                //saveListenRecord(seekBar.getProgress(), SONGIDS.get(musicindex));
                //mp3.release();
                isInCurPage=false;
                Intent intent = new Intent(ListeningMusicActivity.this, DrawUnityActivity.class);
                intent.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, musicindex);
                intent.putExtra(DrawUnityActivity.TAG_SONGPATH, ROOTPATH + songpath);
                intent.putExtra(DrawUnityActivity.TAG_STOPPOSITION, mp3.getCurrentPosition());
                startActivity(intent);
                finish();
            }
        });

    }

    public void startMusic() {
        try {
            if (mp3 != null) {
                mp3.stop();
            }
            mp3.prepare();
            //进入到准备状态

            mp3.start();
            seekBar.setMax(mp3.getDuration());
            //定时器记录播放进度
            if (mTimer != null) {
                mTimer.cancel();
            }
            if (mTimerTask != null) {
                mTimerTask.cancel();
            }
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (isChanging == true) {
                        return;
                    }
                    if (mp3 != null&&isInCurPage) {
                        seekBar.setProgress(mp3.getCurrentPosition());
                    }

                }
            };
            mTimer.schedule(mTimerTask, 0, 10);
            //开始播放
            //state.setText("Playing");  //改变输出信息为“Playing”，下同
        } catch (Exception e) {
            //state.setText(e.toString());//以字符串的形式输出异常
            e.printStackTrace();  //在控制台（control）上打印出异常
        }
    }

    //各按钮单击事件的实现如下
    //开始播放
    private class OnClickListenerStart implements OnClickListener {
        //implementsOnClickListener为实现OnClickListener接口
        @Override
        //重写onClic事件
        public void onClick(View v) {
            //执行的代码，其中可能有异常。一旦发现异常，则立即跳到catch执行。否则不会执行catch里面的内容
            try {
                if (mp3 != null) {
                    mp3.stop();
                }
                mp3.prepare();
                //进入到准备状态

                mp3.start();
                //开始播放
                isInCurPage=true;
            } catch (Exception e) {
                e.printStackTrace();  //在控制台（control）上打印出异常
            }
        }
    }

    //暂停播放
    private class OnClickListenerPause implements OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if (flag == false) //若flag为false，则表示此时播放器的状态为正在播放
                {
                    mp3.pause();
                    flag = true;
                    //state.setText("pause");
                } else if (flag == true) {
                    mp3.start();    //开始播放
                    flag = false;     //重新设置flag为false
                    // state.setText("Playing");
                }
                isInCurPage=true;
            } catch (Exception e) {
                //state.setText(e.toString());
                e.printStackTrace();
            }
        }
    }

    //停止播放
    private class OnClickListenerStop implements OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if (mp3 != null) {
                    mp3.stop();
                    // state.setText("stop");
                }
            } catch (Exception e) {
                // state.setText(e.toString());
                e.printStackTrace();
            }
        }
    }

    //重写暂停状态事件
    protected void onPause() {
        try {
            mp3.release();   //释放音乐资源
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    private class OnClickListenerLast implements OnClickListener {
        @Override
        public void onClick(View v) {
            mp3.stop();
            saveListenRecord(seekBar.getProgress(), SONGIDS.get(musicindex));
            musicindex = (musicindex - 1 + SONGIDS.size()) % SONGIDS.size();
            getSongLyric(SONGIDS.get(musicindex));
            Uri uri = Uri.parse(ROOTPATH + songpath);
            mp3 = MediaPlayer.create(ListeningMusicActivity.this, uri);
            TextView textView = (TextView) findViewById(R.id.lyric_listenmusic);
            textView.setText(lyric);
            startMusic();
        }
    }

    private class OnClickListenerNext implements OnClickListener {
        @Override
        public void onClick(View v) {
            mp3.stop();
            saveListenRecord(seekBar.getProgress(), SONGIDS.get(musicindex));
            musicindex = (musicindex + 1 + SONGIDS.size()) % SONGIDS.size();
            getSongLyric(SONGIDS.get(musicindex));
            Uri uri = Uri.parse(ROOTPATH + songpath);
            mp3 = MediaPlayer.create(ListeningMusicActivity.this, uri);
            TextView textView = (TextView) findViewById(R.id.lyric_listenmusic);
            textView.setText(lyric);
            startMusic();
        }
    }

    private void saveListenRecord(int ms, final String songid) {
        final int listentime = (int) (ms / 1000);
        class MySaveListenRecordThread implements Runnable {
            boolean isSuccess;

            @Override
            public void run() {
                isSuccess = SaveListenRecord.saveListenRecord(useraccount, songid, listentime);
                if (isSuccess) {
                    Log.i("save_listenrecord", "success");
                } else {
                    Log.i("save_listenrecord", "failed");
                }
            }
        }
        // 创建子线程，分别进行Get和Post传输
        new Thread(new MySaveListenRecordThread()).start();

    }

    class MySeekbar implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            isChanging = true;
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            mp3.seekTo(seekBar.getProgress());
            isChanging = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mTimer.cancel();
        mTimerTask.cancel();
        saveListenRecord(seekBar.getProgress(), SONGIDS.get(musicindex));
        mp3.release();
        Intent intent = new Intent(".MenuActivity");
        intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
        intent.putExtra(MenuActivity.TAG_USERNAME, username);
        startActivity(intent);
    }


    public class MyGetSongLyricThread extends Thread {
        private boolean isDone = false;

        @Override
        public void run() {
            info = GetSongWeb.getSongLyric(SONGIDS.get(musicindex));
            Log.i("SONGID", "" + SONGIDS.get(musicindex));
            if (info == null) {
                Log.i("ListenMusic", "song and lyric is empty!");
            } else {
                songpath = info.get(0).get("songpath");
                lyric = info.get(1).get("lyric");
            }
            isDone = true;
        }

        public boolean getIsDone() {
            return isDone;
        }
    }


    public void getSongLyric(String songid) {
        // 创建子线程，分别进行Get和Post传输
        MyGetSongLyricThread thread = new MyGetSongLyricThread();
        thread.start();
        while (!thread.getIsDone()) {

        }
    }
}