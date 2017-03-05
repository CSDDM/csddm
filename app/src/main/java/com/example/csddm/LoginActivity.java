package com.example.csddm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csddm.menu.MenuActivity;
import com.example.csddm.web.LoginWeb;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 2017/2/14.
 */

public class LoginActivity extends AppCompatActivity implements OnClickListener{
    private String account;
    private String password;
    private ProgressDialog dialog;
    private Button loginButton;
    private Button registButton;
    private Button gusetLoginButton;

    // 返回的数据
    private ArrayList<HashMap<String,String>> info ;
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        loginButton = (Button)findViewById(R.id.login_login);
        registButton = (Button)findViewById(R.id.register_login);
        gusetLoginButton = (Button)findViewById(R.id.guest_login);
        // 设置按钮监听器
        loginButton.setOnClickListener(this);
        registButton.setOnClickListener(this);
        gusetLoginButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                EditText account_login = (EditText)findViewById(R.id.account_login);
                EditText password_login = (EditText)findViewById(R.id.password_login);

                account = account_login.getText().toString();
                password = password_login.getText().toString();
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                // 提示框
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                // 创建子线程，分别进行Get和Post传输
                new Thread(new MyLoginThread()).start();
                break;
            case R.id.register_login:
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
                break;
            case R.id.guest_login:
                Log.i("guest_login","in guest_login");
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra(MenuActivity.TAG_USENAME, "游客身份");
                startActivity(intent);
                break;
        }
        ;
    }


    public void skipToMenu(View view){
        //检测身份
        //传输数据
        EditText account_login = (EditText)findViewById(R.id.account_login);
        EditText password_login = (EditText)findViewById(R.id.password_login);

        account = account_login.getText().toString();
        password = password_login.getText().toString();

        if (!checkNetwork()) {
            Toast toast = Toast.makeText(LoginActivity.this,"网络未连接", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        // 创建子线程，分别进行Get和Post传输
        new Thread(new MyLoginThread()).start();

        /*SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("user",new String[]{"name"},"account=? and password=?",
                new String[]{account,password},null,null,null);
        String user_name="";
        while(cursor.moveToNext()){
            user_name=cursor.getString(cursor.getColumnIndex("name"));
        }
        db.close();
        if(!user_name.equals("")){
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra(MenuActivity.TAG_USERACCOUNT, account);
            intent.putExtra(MenuActivity.TAG_USENAME, user_name);
            startActivity(intent);
        }else {//如果用户不存在或者密码账户不匹配
            Log.i("checkLogin","用户不存在或者账户密码不匹配");
        }*/

    }

    // 子线程接收数据，主线程修改数据
    public class MyLoginThread implements Runnable {
        @Override
        public void run() {
            info = LoginWeb.login(account, password);
            dialog.dismiss();
            if(info==null) {
                Log.i("info_login", "content is null");
            } else if (info.get(0).get("username").equals("")) {
                Log.i("REGISTER_FAILURE","登录失败");
            } else {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra(MenuActivity.TAG_USERACCOUNT, account);
                intent.putExtra(MenuActivity.TAG_USENAME, info.get(0).get("username"));
                startActivity(intent);
            }
        }
    }

    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}
