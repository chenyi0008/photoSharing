package com.example.photosharing.api;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;



public class TokenInterceptor implements Interceptor {


    static String appSecret = "29269cb414fd5f7344710a0d3b66c2809d34e";

    static String appId = "1c1b25d8838b4ba3a325407b6fc9e35e";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request modifiedRequest = originalRequest.newBuilder()
                .header("appId", appId) // 添加 token 到请求头
                .header("appSecret", appSecret)
                .build();
        return chain.proceed(modifiedRequest);
    }
}