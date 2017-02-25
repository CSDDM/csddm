package com.example.csddm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.csddm.menu.MenuActivity;
import com.example.csddm.operatedb.SQLiteHelper;

/**
 * Created by dell on 2017/2/14.
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        //检测身份
        //传输数据
        String account = ((EditText)findViewById(R.id.account_register)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_register)).getText().toString();
        String repassword = ((EditText)findViewById(R.id.repassword_register)).getText().toString();
        String name = ((EditText)findViewById(R.id.name_register)).getText().toString();
        if(!password.equals(repassword)){
            Log.i("checkRegister","两次密码输入不一致");
            return;
        }

        SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm_db",null,2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account",account);
        values.put("name",name);
        values.put("password",password);
        long result = db.insert("user",null,values);
        if(result==0) {
            Log.i("chackRegister", "用户已存在");
            return;
        }
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(MenuActivity.TAG_USERACCOUNT, account);
        intent.putExtra(MenuActivity.TAG_USENAME, name);
        startActivity(intent);
    }
}
