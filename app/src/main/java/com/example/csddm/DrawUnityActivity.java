package com.example.csddm;

import android.os.Bundle;
import android.widget.RelativeLayout;

/**
 * Created by dell on 2017/3/2.
 */

public class DrawUnityActivity extends UnityPlayerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawunity);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.unity_drawunity);
        layout.addView(mUnityPlayer.getView());
    }
}
