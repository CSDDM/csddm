package com.example.csddm.drawface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csddm.R;
import com.example.csddm.drawface.res.CustomView;
import com.example.csddm.drawface.res.MyResourse;
import com.example.csddm.menu.MenuActivity;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dell on 2017/2/25.
 */

public class DrawActivity extends FragmentActivity {
    private CustomView customView;
    private RelativeLayout reLayout;
    private boolean isBoy = true;
    private int age;
    private double[] character;
    private String useraccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);
        Intent intent = getIntent();
        isBoy = intent.getBooleanExtra(MyResourse.TAG_ISBOY, true);
        age = intent.getIntExtra(MyResourse.TAG_AGE,0);
        useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
        //年龄，性别,性格数组初始化
        reLayout = (RelativeLayout) findViewById(R.id.rl_persona);
        customView = new CustomView(this, isBoy);
        customView.setDrawingCacheEnabled(true);
        reLayout.addView(customView);

        draw(isBoy,age,character);
    }

    // 点击保存按钮保存自己diy好的图片
    public void keepPhoto(View v) {
        // 对customView截屏并且保存图片到sd卡里
        Bitmap bitmap = customView.getDrawingCache();
        // 给图片命名 以当前时间为准，且以.png格式保存
        String photoName = System.currentTimeMillis() + ".png";
        // 设置保存图片的路径
        String photo_path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + photoName;
        try {
            // 创建文件流
            FileOutputStream os = new FileOutputStream(photo_path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
            // 保存12张图片到sharedPreference
            SaveResource(this, isBoy);
            Toast.makeText(this, "保存成功\n图片保存在" + photo_path, Toast.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 缓冲已经自定义出来的图片
     */
    public void SaveResource(Context context, boolean gender) {
        SharedPreferences sharedPreference;
        if (gender) {
            sharedPreference = context.getSharedPreferences("boy",
                    Context.MODE_PRIVATE);
        } else {
            sharedPreference = context.getSharedPreferences("gril",
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt("发型", customView.resource[0]);
        editor.putInt("脸型", customView.resource[1]);
        editor.putInt("眉毛", customView.resource[2]);
        editor.putInt("眼睛", customView.resource[3]);
        editor.putInt("嘴巴", customView.resource[4]);
        //editor.putInt("特征", customView.resource[5]);
        //editor.putInt("眼镜", customView.resource[6]);
        editor.putInt("衣服", customView.resource[5]);
        //editor.putInt("帽子", customView.resource[8]);
        //editor.putInt("爱好", customView.resource[9]);
        editor.putInt("背景", customView.resource[6]);
        //editor.putInt("气泡", customView.resource[11]);
        editor.commit();
    }

    public void draw(boolean isBoy,int age,double[] character) {
        // 让myView切换图片，重绘图片
        //发型
        drawHair(isBoy,age,character);
        //脸型
        drawFaceShape(isBoy,age,character);
        //眉毛
        drawEyeBrow(isBoy,age,character);
        //眼睛
        drawEye(isBoy,age,character);
        //嘴巴
        drawMouth(isBoy,age,character);
        //衣服
        drawClothes(isBoy,age,character);
        //背景
        drawBackGround(isBoy,age,character);
        customView.invalidate();
    }

    private void drawHair(boolean isBoy,int age,double[] character){
        int[] hair;
        if(isBoy){
            hair = MyResourse.getBoyHair();
        }else{
            if(isYoungAge(age)){
                hair = MyResourse.getYoungAgeGirlHair();
            }else if(isMiddleAge(age)){
                hair = MyResourse.getMiddleAgeGirlHair();
            } else {
                hair = MyResourse.getOldAgeGirlHair();
            }
        }
        //分析性格最接近的头发图片
        int index = 1;
        Bitmap bm = BitmapFactory.decodeResource(getResources(), hair[index]);
        customView.bitmap[0] = bm;
        customView.resource[0] = hair[index];
    }

    private void drawFaceShape(boolean isBoy,int age,double[] character){
        int[] faceshape = MyResourse.getFaceShape();
        //分析性格最接近的脸型图片
        int index = 7;
        Bitmap bm = BitmapFactory.decodeResource(getResources(),faceshape[index]);
        customView.bitmap[1]= bm;
        customView.resource[1] = faceshape[index];
    }

    private void drawEyeBrow(boolean isBoy,int age,double[] character){
        int[] eyebrow = MyResourse.getEyeBrow();
        //分析性格最接近的眉毛图片
        int index = 0;
        Bitmap bm = BitmapFactory.decodeResource(getResources(),eyebrow[index]);
        customView.bitmap[2]= bm;
        customView.resource[2] = eyebrow[index];
    }

    private void drawEye(boolean isBoy,int age,double[] character){
        int[] eye = MyResourse.getEye();
        //分析性格最接近的眼睛图片
        int index = 0;
        Bitmap bm = BitmapFactory.decodeResource(getResources(),eye[index]);
        customView.bitmap[3]= bm;
        customView.resource[3] = eye[index];
    }

    private void drawMouth(boolean isBoy,int age,double[] character){
        int[] mouth;
        if(isYoungAge(age)){
            mouth = MyResourse.getYoungAgeMouth();
        }else if(isMiddleAge(age)){
            if(isBoy){
                mouth = MyResourse.getMiddleAgeBoyMouth();
            }else{
                mouth = MyResourse.getMiddleAgeGirlMouth();
            }
        }else{
            mouth = MyResourse.getOldAgeMouth();
        }
        //分析性格最接近的嘴唇图片
        int index = 3;
        Bitmap bm = BitmapFactory.decodeResource(getResources(),mouth[index]);
        customView.bitmap[4]= bm;
        customView.resource[4] = mouth[index];
    }

    private void drawClothes(boolean isBoy,int age,double[] character){
        int[] clothes;
        if(isBoy){
            clothes = MyResourse.getBoyClothes();
        }else{
            clothes = MyResourse.getGirlClothes();
        }
        //分析性格最接近的衣服图片
        int index = 7;
        Bitmap bm = BitmapFactory.decodeResource(getResources(),clothes[index]);
        customView.bitmap[5]= bm;
        customView.resource[5] = clothes[index];
    }

    private void drawBackGround(boolean isBoy,int age,double[] character){
        int[] background = MyResourse.getBackGround();
        //随机选取一张背景图片
        int index = (int)(Math.random()*background.length);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),background[index]);
        customView.bitmap[6]= bm;
        customView.resource[6] = background[index];
    }

    private boolean isYoungAge(int age){
        if(0<=age&&age<=25){
            return true;
        }
        return false;
    }

    private boolean isMiddleAge(int age){
        if(26<=age&&age<=50){
            return true;
        }
        return false;
    }

    private boolean isOldAge(int age){
        if(age>50){
            return true;
        }
        return false;
    }

    public void analysisCharacter(){

    }
}
