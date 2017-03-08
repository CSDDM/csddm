package com.example.csddm;

import com.example.csddm.menu.MenuActivity;
import com.unity3d.player.*;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UnityPlayerActivity extends Activity {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        //mUnityPlayer = new UnityPlayer(this);
        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }

    //注册广播接受者
    private void registBroadcast() {
        BroadcastReceiver receiver = new FinishUnityBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finishUnity");
        registerReceiver(receiver, filter);
        Log.e("UnityPlayerActivity","registBroad");
    }

    public class FinishUnityBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("UnityPlayerActivity","FinishUnityBroadcast");
            mUnityPlayer.quit();
            Log.e("UnityPlayerActivity","FinishUnityBroadcast");
            String useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
            String username = intent.getStringExtra(MenuActivity.TAG_USERNAME);
            int musicindex = intent.getIntExtra(ListeningMusicActivity.TAG_MUSICINDEX,0);
            int position = intent.getIntExtra(ListeningMusicActivity.TAG_STARTPOSITION,0);
            Intent intent2 = new Intent(".ListeningMusicActivity");
            intent2.putExtra(MenuActivity.TAG_USERACCOUNT,useraccount);
            intent2.putExtra(MenuActivity.TAG_USERNAME,username);
            intent2.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, musicindex);
            intent2.putExtra(ListeningMusicActivity.TAG_STARTPOSITION, position);
            startActivity(intent2);
        }
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
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

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}
