package com.example.csddm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.csddm.drawface.DrawActivity;
import com.example.csddm.drawface.res.MyResourse;
import com.example.csddm.menu.MenuActivity;
import com.example.csddm.operatedb.SQLiteHelper;

/**
 * Created by dell on 2017/2/24.
 */

public class ToDrawActivity extends AppCompatActivity {
    private String useraccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todraw);
    }

    public void skipToDraw(View view){
        int age = Integer.parseInt(((EditText)findViewById(R.id.age_todraw)).getText().toString());
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.gender_todraw);
        boolean isBoy = true;
        if(radioGroup.getCheckedRadioButtonId()==R.id.female){
            isBoy=false;
        }else{
            isBoy = true;
        }

        Intent intent = new Intent(this, DrawActivity.class);
        intent.putExtra(MyResourse.TAG_AGE, age);
        intent.putExtra(MyResourse.TAG_ISBOY, isBoy);
        intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
        startActivity(intent);
    }

}
