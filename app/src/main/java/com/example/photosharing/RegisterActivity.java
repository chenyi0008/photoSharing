package com.example.photosharing;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);
        etAccount = findViewById(R.id.et_account);
        TextView btLogin = findViewById(R.id.tv_login);
        Button btSign=findViewById(R.id.bt_sign);

        btSign.setOnClickListener(this);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
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
    }
    @Override
    public void onClick(View view) {
        final CountDownLatch latch = new CountDownLatch(1);
        User u = new User();
        u.setUsername(etAccount.getText().toString());
        u.setPassword(etPwd.getText().toString());

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.register(u);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
//                    System.out.println(response.body().getMsg());

                    if(response.body().getMsg() == null) {
                        Toast.makeText(RegisterActivity.this,
                                "注册成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                    }


                    else{
                        Toast.makeText(RegisterActivity.this,
                                response.body().getMsg(),
                                Toast.LENGTH_SHORT).show();
                    }



                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败，处理错误情况");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                // 处理网络请求失败
                System.out.println("处理网络请求失败");
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace());
                System.out.println(t.getStackTrace());
            }
        });

        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
