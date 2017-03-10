package com.example.csddm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Window;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.csddm.menu.MenuActivity;
import com.example.csddm.web.GetPictureRecordWeb;
import com.example.csddm.web.IPWeb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.csddm.MyHorizontalScrollView.CurrentImageChangeListener;
import com.example.csddm.MyHorizontalScrollView.OnItemClickListener;

public class RecordActivity extends AppCompatActivity {
    private String useraccount;
    private String username;
    private String lyric;
    private final String PICTUREPATH = "http://" + IPWeb.IP + "/SYCserver/picture/";
    private MyHorizontalScrollView mHorizontalScrollView;
    private HorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;
    //    private List<Bitmap> mDatas = new ArrayList<Integer>(Arrays.asList(
//            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
//            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
//            R.drawable.l));
    private List<String> path=new ArrayList<>();
    private List<Bitmap> mDatas=new ArrayList<>();

    private int index=0;
    private String urlpath="";
    //private List<String> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);
        Intent intent = getIntent();
        useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
        username = intent.getStringExtra(MenuActivity.TAG_USERNAME);
        mImg = (ImageView) findViewById(R.id.id_content);
        getPictureRecord();
        if(path!=null) {
            for(index=0;index<path.size();index++) {
                urlpath=PICTUREPATH+path.get(index)+".jpg";
                getBitmap();
            }
        }

        mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
        mAdapter = new HorizontalScrollViewAdapter(this, mDatas, path);
        //添加滚动回调
        mHorizontalScrollView
                .setCurrentImageChangeListener(new CurrentImageChangeListener() {
                    @Override
                    public void onCurrentImgChanged(int position,
                                                    View viewIndicator) {
//                        mImg.setImageResource(mDatas.get(position));
                        mImg.setImageBitmap(mDatas.get(position));
                        viewIndicator.setBackgroundColor(Color
                                .parseColor("#AA024DA4"));
                    }
                });
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                mImg.setImageBitmap(mDatas.get(position));
//                mImg.setImageResource(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);
    }

    public class GetPictureRecordThread extends Thread {
        private boolean isDone = false;

        @Override
        public void run() {
            path = GetPictureRecordWeb.getPictureRecord(useraccount);
            isDone = true;
        }

        public boolean getIsDone() {
            return isDone;
        }

    }

    public void getPictureRecord() {
        // 创建子线程，分别进行Get和Post传输
        GetPictureRecordThread thread = new GetPictureRecordThread();
        thread.start();
        while (!thread.getIsDone()) {

        }
    }
    public class GetBitmapThread extends Thread {
        private boolean isDone = false;

        @Override
        public void run() {
            try {
                mDatas.add(index,getBitmapFromUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            isDone = true;
        }

        public boolean getIsDone() {
            return isDone;
        }

    }

    public void getBitmap() {
        // 创建子线程，分别进行Get和Post传输
        GetBitmapThread thread = new GetBitmapThread();
        thread.start();
        while (!thread.getIsDone()) {

        }
    }

    public Bitmap getBitmapFromUrl() throws IOException {

        URL url = new URL(urlpath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
