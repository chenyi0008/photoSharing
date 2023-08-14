package com.example.photosharing.util;

import android.media.Image;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.MyFile;
import com.example.photosharing.model.ResponseBody;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Uploader {



    public static void uploadImage(File file) {
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);

        RetrofitRequest_Interface r = MyRetrofit.getRetrofitRequestInterface();

        Call<ResponseBody<MyFile>> call = r.uploadFile(filePart);
        System.out.println("-----------------------");
        System.out.println(call.isCanceled());
        System.out.println(call.timeout());
        System.out.println("-----------------------");

        call.enqueue(new Callback<ResponseBody<MyFile>>() {

            @Override
            public void onResponse(Call<ResponseBody<MyFile>> call, Response<ResponseBody<MyFile>> response) {
                if (response.isSuccessful()) {
                    // 处理成功响应
                    System.out.println("succeed");
                    System.out.println(response.body().getData());
                } else {
                    // 处理响应失败情况
                    System.out.println("failed-1");
//                    listener.onImageUploadFailure("Upload failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<MyFile>> call, Throwable t) {
                // 处理网络请求失败情况
//                listener.onImageUploadFailure(t.getMessage());
                System.out.println("failed-2");
            }
        });
        System.out.println("----------end---------");
    }

}
