package com.example.csddm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.csddm.drawface.res.MyResourse;
import com.example.csddm.menu.MenuActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ListeningMusicActivity extends AppCompatActivity {       //声名变量
    public static final String TAG_MUSICINDEX ="musicIndex";
    private int musicindex;//记录当前音乐在音乐列表中的下标
    private String useraccount;
    private Button start = null;
    private Button pause = null;
    private ArrayList<String> musicList = null;
    private Button last = null;
    private Button next = null;
    private MediaPlayer mp3;
    SeekBar seekBar;
    private boolean flag = false; //设置标记，false表示正在播放
    private boolean isChanging = false;//互斥变量，防止定时器与seekbar拖动时进度冲突
    final private int [] song = MyResourse.getSong();
    final private int[] lyric = MyResourse.getLyric();
    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_listening_music);
        Intent intent = getIntent();
        musicindex = intent.getIntExtra(TAG_MUSICINDEX,0);
        Log.i("musicIndex",""+musicindex);
        useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
        //取得各按钮组件
        start = (Button) super.findViewById(R.id.play);
        pause = (Button) super.findViewById(R.id.pause);
        last = (Button) findViewById(R.id.last);
        next = (Button) findViewById(R.id.next);
        //为每个按钮设置单击事件
        start.setOnClickListener(new OnClickListenerStart());
        pause.setOnClickListener(new OnClickListenerPause());
        last.setOnClickListener(new OnClickListenerLast());
        next.setOnClickListener(new OnClickListenerNext());
        //设置歌词
        TextView textView = (TextView)findViewById(R.id.lyric_listenmusic);
        textView.setText(getString(getResources().openRawResource(lyric[musicindex])));
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        mp3 = new MediaPlayer();    //创建一个MediaPlayer对象
        //在res下新建一个raw文件夹把一首歌放到此文件夹中并用英文命名
        mp3 = MediaPlayer.create(ListeningMusicActivity.this, song[musicindex]);
        startMusic();
        //悬浮框用于点击进入歌曲3D场景界面
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_listenmusic);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "歌曲3D场景", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //滚动条
        seekBar = (SeekBar)findViewById(R.id.seekbar_listenmusic);
        seekBar.setOnSeekBarChangeListener(new MySeekbar());

    }

    public String getString(InputStream inputStream){
        InputStreamReader inputStreamReader = null;
        try{
            inputStreamReader = new InputStreamReader(inputStream,"gbk");
        }catch(UnsupportedEncodingException e1){
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try{
            while((line = reader.readLine())!=null){
                sb.append(line);
                sb.append("\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void startMusic(){
        try {
            if (mp3 != null) {
                mp3.stop();
            }
            mp3.prepare();
            //进入到准备状态

            mp3.start();
            seekBar.setMax(mp3.getDuration());
            //定时器记录播放进度
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if(isChanging==true){
                        return;
                    }
                    seekBar.setProgress(mp3.getCurrentPosition());
                }
            };
            mTimer.schedule(mTimerTask,0,10);
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
                //state.setText("Playing");  //改变输出信息为“Playing”，下同
            } catch (Exception e) {
                //state.setText(e.toString());//以字符串的形式输出异常
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
            musicindex = (musicindex-1+song.length)%song.length;
            mp3 = MediaPlayer.create(ListeningMusicActivity.this, song[musicindex]);
            TextView textView = (TextView)findViewById(R.id.lyric_listenmusic);
            textView.setText(getString(getResources().openRawResource(lyric[musicindex])));
            startMusic();
        }
    }

    private class OnClickListenerNext implements OnClickListener {
        @Override
        public void onClick(View v) {
            mp3.stop();
            musicindex = (musicindex+1+song.length)%song.length;
            mp3 = MediaPlayer.create(ListeningMusicActivity.this, song[musicindex]);
            TextView textView = (TextView)findViewById(R.id.lyric_listenmusic);
            textView.setText(getString(getResources().openRawResource(lyric[musicindex])));
            startMusic();
        }
    }

    class MySeekbar implements SeekBar.OnSeekBarChangeListener{
        public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){

        }

        public void onStartTrackingTouch(SeekBar seekBar){
            isChanging=true;
        }

        public void onStopTrackingTouch(SeekBar seekBar){
            mp3.seekTo(seekBar.getProgress());
            isChanging = false;
        }
    }
}