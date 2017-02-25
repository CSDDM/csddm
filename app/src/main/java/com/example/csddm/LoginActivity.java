package com.example.csddm;

import android.content.Intent;
import android.database.Cursor;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void skipToMenu(View view){
        //检测身份
        //传输数据
        EditText account_login = (EditText)findViewById(R.id.account_login);
        EditText password_login = (EditText)findViewById(R.id.password_login);

        String account = account_login.getText().toString();
        String password = password_login.getText().toString();
        SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm_db",null,2);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("user",new String[]{"name"},"account=? and password=?",
                new String[]{account,password},null,null,null);
        String user_name="";
        while(cursor.moveToNext()){
            user_name=cursor.getString(cursor.getColumnIndex("name"));
        }

        if(!user_name.equals("")){
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra(MenuActivity.TAG_USERACCOUNT, account);
            intent.putExtra(MenuActivity.TAG_USENAME, user_name);
            startActivity(intent);
        }else {//如果用户不存在或者密码账户不匹配
            Log.i("checkLogin","用户不存在或者账户密码不匹配");
        }
    }

    //游客身份登陆
    public void skipToMenuTour(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_name", "游客身份");
        startActivity(intent);
    }

    public void skipToRegister(View view){
        startActivity(new Intent(".Register"));
    }
}
