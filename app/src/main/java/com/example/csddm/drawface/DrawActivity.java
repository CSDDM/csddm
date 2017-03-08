package com.example.csddm.drawface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
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
import com.example.csddm.entity.ListenRecord;
import com.example.csddm.menu.MenuActivity;
import com.example.csddm.operatedb.QueryData;
import com.example.csddm.operatedb.SQLiteHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2017/2/25.
 */

public class DrawActivity extends AppCompatActivity {
    private CustomView customView;
    private TextView textView;
    private RelativeLayout reLayout;
    private boolean isBoy = true;
    private boolean isDefault;
    private int age=0;
    private double[] character = new double[14];
    private String useraccount;
    private Map<String, int[]> characterMap = new LinkedHashMap<String, int[]>();
    private String[] labels = {"柔情甜蜜","励志","伤感","诙谐","清新欢快","激情","古风"};
    private String[] characters={"外向","内向","冷静","冲动" ,"热情","典雅","乐观","悲观","富有情调","无趣","循规蹈矩","放荡不羁","多愁善感","幽默"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);
        Intent intent = getIntent();
        isDefault = false;
        isBoy = intent.getBooleanExtra(MyResourse.TAG_ISBOY, true);
        age = intent.getIntExtra(MyResourse.TAG_AGE,0);
        useraccount = intent.getStringExtra(MenuActivity.TAG_USERACCOUNT);
        initCharacters();
        //年龄，性别,性格数组初始化
        reLayout = (RelativeLayout) findViewById(R.id.rl_persona);
        customView = new CustomView(this, isBoy);
        customView.setDrawingCacheEnabled(true);
        textView=(TextView)findViewById(R.id.result_persona);
        reLayout.addView(customView);
        //悬浮框用于点击进入歌曲3D场景界面
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_persona);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "保存图片", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveImageWithoutChoose(view);
               // saveImage(view);
            }
        });
        draw(isBoy,age,character);
    }

    public void saveImageWithoutChoose(View v){
        Bitmap bitmap = customView.getDrawingCache();
        grantUriPermission(Environment.getExternalStorageDirectory()+"/CSDDM",Uri.parse("file://"+ Environment.getExternalStorageDirectory()),Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
        SaveResource(this, isBoy);
        Toast.makeText(this, "保存成功\n图片保存在手机图库中", Toast.LENGTH_SHORT)
                .show();
    }

    public void saveImage(View v) {
        Bitmap bitmap = customView.getDrawingCache();
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "DCIM/Camera/CSDDM");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            // 保存12张图片到sharedPreference
            SaveResource(this, isBoy);
            Toast.makeText(this, "保存成功\n图片保存在" + Environment.getExternalStorageDirectory()+"/CSDDM", Toast.LENGTH_SHORT)
                    .show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 点击保存按钮保存自己diy好的图片
    public void keepPhoto(View v) {
        // 对customView截屏并且保存图片到sd卡里
        Bitmap bitmap = customView.getDrawingCache();
        // 给图片命名 以当前时间为准，且以.png格式保存
        String photoName = "test.png";
//        String photoName = System.currentTimeMillis() + ".png";
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
        analysisCharacter();
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
        String result="分析结果：\n";
        for(int i=0;i<character.length;i++) {
            if(character[i]!=0) {
                result+=characters[i]+"  --  "+(int)(character[i])+"\n";
            }
        }
        SpannableString msp = new SpannableString(result);

        //设置字体(default,default-bold,monospace,serif,sans-serif)
        msp.setSpan(new TypefaceSpan("monospace"), 0, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.WHITE), 0, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
        //msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // textView.setText(result);
        textView.setText(msp);
    }

    private void drawHair(boolean isBoy,int age,double[] character){
        int[] hair;
        double[][] characterData;
        if(isBoy){
            hair = MyResourse.getBoyHair();
            characterData=QueryData.getS1CharacterData();
        }else{
            if(isYoungAge(age)){
                hair = MyResourse.getYoungAgeGirlHair();
                characterData=QueryData.getS2YoungCharacterData();
            }else if(isMiddleAge(age)){
                hair = MyResourse.getMiddleAgeGirlHair();
                characterData=QueryData.getS2MiddleCharacterData();
            } else {
                hair = MyResourse.getOldAgeGirlHair();
                characterData=QueryData.getS2OldCharacterData();
            }
        }
        //分析性格最接近的头发图片
        int index = 1;
        if(!isDefault)
            index=getClosestPicture(characterData,character);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), hair[index]);
        customView.bitmap[0] = bm;
        customView.resource[0] = hair[index];
    }

    private void drawFaceShape(boolean isBoy,int age,double[] character){
        int[] faceshape = MyResourse.getFaceShape();
        double[][] characterData=QueryData.getS5CharacterData();
        //分析性格最接近的脸型图片
        int index = 7;
        if(!isDefault)
            index=getClosestPicture(characterData,character);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),faceshape[index]);
        customView.bitmap[1]= bm;
        customView.resource[1] = faceshape[index];
    }

    private void drawEyeBrow(boolean isBoy,int age,double[] character){
        int[] eyebrow = MyResourse.getEyeBrow();
        double[][] characterData=QueryData.getS6CharacterData();
        //分析性格最接近的眉毛图片
        int index = 1;
        if(!isDefault)
            index=getClosestPicture(characterData,character);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),eyebrow[index]);
        customView.bitmap[2]= bm;
        customView.resource[2] = eyebrow[index];
    }

    private void drawEye(boolean isBoy,int age,double[] character){
        int[] eye = MyResourse.getEye();
        double[][] characterData=QueryData.getS7CharacterData();
        //分析性格最接近的眼睛图片
        int index = 1;
        if(!isDefault)
            index=getClosestPicture(characterData,character);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),eye[index]);
        customView.bitmap[3]= bm;
        customView.resource[3] = eye[index];
    }

    private void drawMouth(boolean isBoy,int age,double[] character){
        int[] mouth;
        double[][] characterData;
        if(isYoungAge(age)){
            mouth = MyResourse.getYoungAgeMouth();
            characterData=QueryData.getS8CharacterData();
        }else if(isMiddleAge(age)){
            if(isBoy){
                mouth = MyResourse.getMiddleAgeBoyMouth();
                characterData=QueryData.getS9CharacterData();
            }else{
                mouth = MyResourse.getMiddleAgeGirlMouth();
                characterData=QueryData.getS10CharacterData();
            }
        }else{
            mouth = MyResourse.getOldAgeMouth();
            characterData=QueryData.getS11CharacterData();
        }
        //分析性格最接近的嘴唇图片
        int index = 1;
        if(!isDefault)
            index=getClosestPicture(characterData,character);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),mouth[index]);
        customView.bitmap[4]= bm;
        customView.resource[4] = mouth[index];
    }

    private void drawClothes(boolean isBoy,int age,double[] character){
        int[] clothes;
        double[][] characterData;
        if(isBoy){
            if(isYoungAge(age)){
                clothes = MyResourse.getYoungBoyClothes();
                characterData=QueryData.getS12YoungCharacterData();
            }else if(isMiddleAge(age)){
                clothes = MyResourse.getMiddleBoyClothes();
                characterData=QueryData.getS12MiddleCharacterData();
            }else{
                clothes = MyResourse.getOldBoyClothes();
                characterData=QueryData.getS12OldCharacterData();
            }
        }else{
            if(isYoungAge(age)){
                clothes = MyResourse.getYoungGirlClothes();
                characterData=QueryData.getS13YoungCharacterData();
            }else if(isMiddleAge(age)){
                clothes = MyResourse.getMiddleGirlClothes();
                characterData=QueryData.getS13MiddleCharacterData();
            }else{
                clothes = MyResourse.getOldGirlClothes();
                characterData=QueryData.getS13OldCharacterData();
            }
        }
        //分析性格最接近的衣服图片
        int index = 1;
        if(!isDefault)
            index=getClosestPicture(characterData,character);
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

    //初始化性格分析列表
    private void initCharacters() {
        int[][] characters={
                {8,6,12,11,0,4,12},
                {-1,4,12,6,-1,3,-1},
                {-1,10,9,13,10,10,-1},
                {8,6,12,13,6,3,5},
                {2,6,1,11,6,4,5},
                {8,6,7,13,0,3,-1},
                {12,6,7,13,0,4,5},
                {8,-1,12,13,0,3,5},
                {2,-1,12,13,0,11,-1},
                {-1,2,-1,4,0,-1,-1},};
        characterMap.put("交响乐",characters[0]);
        characterMap.put("摇滚",characters[1]);
        characterMap.put("进行曲",characters[2]);
        characterMap.put("流行",characters[3]);
        characterMap.put("古典",characters[4]);
        characterMap.put("爵士",characters[5]);
        characterMap.put("乡村音乐",characters[6]);
        characterMap.put("舞曲",characters[7]);
        characterMap.put("歌剧戏曲二人转",characters[8]);
        characterMap.put("儿童歌谣",characters[9]);
    }

    public void analysisCharacter(){
        for(int i=0;i<character.length;i++)
            character[i]=0;
        SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm",null,1);
        ArrayList<ListenRecord> listenRecord = new QueryData().getListenRecordByAccount(useraccount,dbHelper);
        ListenRecord entry;
        for(int i = 0;i < listenRecord.size(); i ++){
            entry = listenRecord.get(i);
            //获取歌曲时长
            double songTime=entry.getSong().getTime();
            //获取听此首歌曲的总时长
            double totalTime=entry.getTime();
            //获取歌曲风格
            String style=entry.getSong().getStyle();
            //获取此风格歌曲的每种情感对应的性格分析列表
            int[] characterArray=characterMap.get(style);
            //歌曲标签（即情感）列表
            String[] labelArray =entry.getSong().getLabel();
            //逐标签分析
            for(int j=0;j<labelArray.length;j++) {
                //获得此种标签在标签列表中的下标
                int index=getLabelIndex(labelArray[j]);
                //如果有对应的类型分析，就根据标签下标，
                // 在性格分析列表中寻找对应的性格分析结果，
                // 并将权重加入到最终结果上
                if(characterArray[index]>=0) {
                    character[characterArray[index]]=character[characterArray[index]]+totalTime/songTime;
                }
            }
        }
        character=changeToWeight(character);
    }

    //获得相应标签在标签列表中的下标
    private int getLabelIndex(String label) {
        for(int i=0;i<labels.length;i++) {
            if(label.equals(labels[i]))
                return i;
        }
        return -1;
    }

    //根据听歌记录分析出的性格权重标准化
    private double[] changeToWeight(double[] character) {
        DecimalFormat df = new DecimalFormat("#.00");
        double total=0;
        for(int i=0;i<character.length;i++) {
            total+=character[i];
        }
        if(total>0) {//权重总和不为零
            for(int j=0;j<character.length;j++) {
                character[j]=Double.parseDouble(df.format(character[j]/total))*100;
            }
        }
        else //权重总和为零，说明没有记录无法分析，设置成默认显示
            isDefault=true;
        return character;
    }

    //寻找最近接图片的下标
    private int getClosestPicture(double[][] data,double[] character) {
        //记录最小的差距
        double minDifference=Double.MAX_VALUE;
        //记录最接近图片的下标
        int index=-1;
        //遍历图片数据，寻找差距最小（即最接近）的图片
        for(int i = 0;i < data.length;i ++) {
            double difference=0;
            for(int j=0;j<character.length;j++) {
                difference+=Math.pow(character[j]-data[i][j],2);
            }
            if(difference<minDifference) {//不断替换最小差距
                minDifference=difference;
                index=i;
            }
        }
        return index;
    }
}
