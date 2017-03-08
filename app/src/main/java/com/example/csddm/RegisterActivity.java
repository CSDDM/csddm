package com.example.csddm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csddm.menu.MenuActivity;
import com.example.csddm.web.RegisterWeb;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 2017/2/14.
 */

public class RegisterActivity extends AppCompatActivity {
    private String account;
    private String password;
    private String repassword;
    private String username; // 返回的数据
    private ProgressDialog dialog;
    private ArrayList<HashMap<String,String>> info = new ArrayList<>();
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        //检测身份
        //传输数据
        account = ((EditText)findViewById(R.id.account_register)).getText().toString();
        password = ((EditText)findViewById(R.id.password_register)).getText().toString();
        repassword = ((EditText)findViewById(R.id.repassword_register)).getText().toString();
        username = ((EditText)findViewById(R.id.name_register)).getText().toString();

        if (!checkNetwork()) {
            Toast toast = Toast.makeText(RegisterActivity.this,"网络未连接", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        if(!password.equals(repassword)){
            Toast.makeText(RegisterActivity.this,"两次密码输入不一致", Toast.LENGTH_LONG).show();
            return;
        }
        dialog = new ProgressDialog(this);
        // 创建子线程，分别进行Get和Post传输
        new Thread(new RegisterActivity.MyRegisterThread()).start();
    }

    // 子线程接收数据，主线程修改数据
    public class MyRegisterThread implements Runnable {
        @Override
        public void run() {
            info = RegisterWeb.register(account, password,username);
            // info = WebServicePost.executeHttpPost(username.getText().toString(), password.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // infotv.setText(info);
                    if(info==null){
                        Log.i("register_info","data is null");
                    }

                    if(info.get(0).get("username").equals("")){
                        Log.i("Register","该账户用户已存在");
                        dialog.setTitle("注册失败");
                        dialog.setMessage("该账户用户已存在");
                        dialog.setCancelable(true);
                        dialog.show();
                    }else{
                        Intent intent = new Intent(RegisterActivity.this,MenuActivity.class);
                        intent.putExtra(MenuActivity.TAG_USERACCOUNT, account);
                        intent.putExtra(MenuActivity.TAG_USERNAME, username);
                        startActivity(intent);
                        finish();
                    }
                }
            });
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
