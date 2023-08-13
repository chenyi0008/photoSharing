package com.example.photosharing;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;

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

    @Test
    public void addition_isCorrect() throws InterruptedException {
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
                    System.out.println(response);
                    System.out.println("注册成功，处理响应");
                } else {
                    // 注册失败，处理错误情况
                    System.out.println(response.code());
                    System.out.println("注册失败，处理错误情况");
                    System.out.println(response.body());
                    System.out.println(response);
                    System.out.println("request:");
                    Request request = call.request();
                    System.out.println(request);
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
}