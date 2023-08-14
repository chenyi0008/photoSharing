package com.example.photosharing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.UserInfoUpdateDto;
import com.example.photosharing.util.Uploader;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    CountDownLatch latch;
    @Before
    public void before(){
        latch = new CountDownLatch(1);
    }

    @After
    public void after() throws InterruptedException {
        latch.await(2, TimeUnit.SECONDS);
    }

    /**
     * 测试注册
     * @throws InterruptedException
     */
    @Test
    public void register() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        User u = new User();
        u.setUsername("hello");
        u.setPassword("admin");

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.register(u);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("注册成功，处理响应");

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("注册失败，处理错误情况");


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 处理网络请求失败
                System.out.println("处理网络请求失败");
            }
        });

        latch.await(2, TimeUnit.SECONDS);

    }



    /**
     * 测试登录
     * @throws InterruptedException
     */
    @Test
    public void login() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);


        String username = "hello";
        String password = "admin123";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = httpUtil.login(username, password);

        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
                    System.out.println(response.body());

                    UserInfo userInfo = response.body().getData();
                    System.out.println(userInfo);
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
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

        latch.await(2, TimeUnit.SECONDS);

    }

    /**
     * 上传文件   未完成
     */
    @Test
    public void upload() throws InterruptedException {

        String filePath = "D:\\Desktop\\小豆泥表情合集\\-2b2a0513a122b71e.jpg";
        File file = new File(filePath);
        Uploader.uploadImage(file);

    }


    @Test
    public void UserUpdate(){
        UserInfoUpdateDto u = new UserInfoUpdateDto();
        u.setAvatar("https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/14/41d6946b-b975-4667-9bca-58f4b31a4fc3.jpg");
        u.setUsername("user1");
        u.setId("1690646693722329088");
        u.setIntroduce("helloaaa");//注意 不能传中文 传中文会报错
        u.setSex("1");//sex=1时 为男性  sex=0时 为女性

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.updateUser(u);
        System.out.println(call.request().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("修改成功");


                } else {
                    // 注册失败，处理错误情况
                    System.out.println("修改失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }






}