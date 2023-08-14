package com.example.photosharing;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;
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

    /**
     * 测试注册
     * @throws InterruptedException
     */
    @Test
    public void register() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        User u = new User();
        u.setUsername("admin333545");
        u.setPassword("admin");

        RetrofitRequest_Interface retrofitRequestInterface = MyRetrofit.getRetrofitRequestInterface();

        Call<ResponseBody> call = retrofitRequestInterface.register(u);

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

        latch.await(5, TimeUnit.SECONDS);

    }



    /**
     * 测试登录
     * @throws InterruptedException
     */
    @Test
    public void login() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        String username = "admin";
        String password = "admin";

        RetrofitRequest_Interface retrofitRequestInterface = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = retrofitRequestInterface.login(username, password);

        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("登录成功，处理响应");
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

        latch.await(5, TimeUnit.SECONDS);

    }

    /**
     * 上传文件   未完成
     */
    @Test
    public void upload(){
//        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String filePath = "D:\\Desktop\\小豆泥表情合集\\-2b2a0513a122b71e.jpg";
        File file = new File(filePath);

        Uploader.uploadImage(file);

    }


//    @Test




}