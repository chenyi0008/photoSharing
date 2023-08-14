package com.example.photosharing.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {


    private static String baseUrl = "http://47.107.52.7:88/";

//    private static String baseUrl = "http://127.0.0.1:8888/";



    private static Retrofit retrofit;

    private static String globalToken;

    public static String getToken() {
        return globalToken;
    }

    public static void setToken(String token) {
        globalToken = token;
    }

    public static Retrofit InitInstance() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new TokenInterceptor()) // 添加拦截器并传递 token
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    public static RetrofitRequest_Interface getRetrofitRequestInterface(){
        if (retrofit == null){
            retrofit = InitInstance();
        }
        return retrofit.create(RetrofitRequest_Interface.class);
    }

    public static void RefreshUserInfo(){

        RetrofitRequest_Interface retrofitRequestInterface = getRetrofitRequestInterface();


    }
}
