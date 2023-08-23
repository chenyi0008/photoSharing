package com.example.photosharing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.UserInfo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etAccount;
    private CheckBox cbRememberPwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);
        etAccount = findViewById(R.id.et_account);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        Button btLogin = findViewById(R.id.bt_login);
        TextView btSign=findViewById(R.id.tv_sign_up);

        btLogin.setOnClickListener(this);
        btSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (bPwdSwitch) {
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_24);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_off_24);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });

        String spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        String accountKey = getResources()
                .getString(R.string.login_account_name);
        String passwordKey = getResources()
                .getString(R.string.login_password);
        String rememberPasswordKey = getResources()
                .getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        String account = spFile.getString(accountKey,null);
        String password = spFile.getString(passwordKey,null);
        Boolean rememberPassword = spFile.getBoolean(rememberPasswordKey, false);
        if (account != null && !TextUtils.isEmpty(account)) {
            etAccount.setText(account);
        }
        if (password != null && !TextUtils.isEmpty (password)) {
            etPwd.setText(password);
        }
        cbRememberPwd.setChecked(rememberPassword);


    }
    @Override
    public void onClick(View view) {
        final CountDownLatch latch = new CountDownLatch(1);

        String username = etAccount.getText().toString();
        String password = etPwd.getText().toString();

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = httpUtil.login(username, password);

        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    System.out.println("请求成功，处理响应");
                    System.out.println(response.body());

                    UserInfo userInfo=response.body().getData();
                    UserInfo.SetInstance(userInfo);

                    if (response.body().getMsg().equals("登录成功")){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //System.out.println(userInfo);
                        System.out.println(response.body().getMsg());
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);

                        //记住密码
                        String spFileName = getResources()
                                .getString(R.string.shared_preferences_file_name);
                        String accountKey = getResources()
                                .getString(R.string.login_account_name);
                        String passwordKey = getResources()
                                .getString(R.string.login_password);
                        String rememberPasswordKey = getResources()
                                .getString(R.string.login_remember_password);
                        SharedPreferences spFile = getSharedPreferences(
                                spFileName,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = spFile.edit();
                        if (cbRememberPwd.isChecked()) {
                            String account = etAccount.getText().toString();
                            String password = etPwd.getText().toString();

                            editor.putString(accountKey, account);
                            editor.putString(passwordKey, password);
                            editor.putBoolean(rememberPasswordKey, true);
                            editor.apply();
                        }else{
                            editor.remove(accountKey);
                            editor.remove(passwordKey);
                            editor.remove(rememberPasswordKey);
                            editor.apply();
                        }


                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    System.out.println("登录失败，处理错误情况");
                    System.out.println(call.request().url());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<UserInfo>> call, Throwable t) {
                // 处理网络请求失败
                System.out.println("处理网络请求失败");
            }
        });

    }

}