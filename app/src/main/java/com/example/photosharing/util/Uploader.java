package com.example.photosharing.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.MyFile;
import com.example.photosharing.model.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Uploader {


    /**
     * 上传单个文件
     * @param file
     * @return 单个图片的url
     */
    public static String uploadImage(File file) {
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("fileList", file.getName(), fileRequestBody);

        RetrofitRequest_Interface r = MyRetrofit.getRetrofitRequestInterface();
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        list.add(filePart);

        Call<ResponseBody<MyFile>> call = r.uploadFile(list);
        final String[] result = {null};
        call.enqueue(new Callback<ResponseBody<MyFile>>() {

            @Override
            public void onResponse(Call<ResponseBody<MyFile>> call, Response<ResponseBody<MyFile>> response) {
                if (response.isSuccessful()) {
                    // 处理成功响应
                    System.out.println("上传成功");
                    System.out.println("图片链接:" + response.body().getData().getImageUrlList().get(0));
                    result[0] = response.body().getData().getImageUrlList().get(0);
                } else {
                    // 处理响应失败情况
                    System.out.println("failed-1");
                    System.out.println(response.code());
//                    listener.onImageUploadFailure("Upload failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<MyFile>> call, Throwable t) {
                // 处理网络请求失败情况
                System.out.println("failed-2");
            }
        });
        return result[0];
    }

    /**
     * 批量上传文件
     * @param fileList
     * @return 返回ImageCode
     */
    public static String uploadImageBatch(List<File> fileList){


        RetrofitRequest_Interface r = MyRetrofit.getRetrofitRequestInterface();
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (File file : fileList) {
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("fileList", file.getName(), fileRequestBody);
            list.add(filePart);
        }
        final String[] result = {null};
        Call<ResponseBody<MyFile>> call = r.uploadFile(list);
        call.enqueue(new Callback<ResponseBody<MyFile>>() {

            @Override
            public void onResponse(Call<ResponseBody<MyFile>> call, Response<ResponseBody<MyFile>> response) {
                if (response.isSuccessful()) {
                    // 处理成功响应
                    System.out.println("上传成功");
                    System.out.println(response.body().getMsg());
                    System.out.println("图片ImageCode:" + response.body().getData().getImageCode());
                    result[0] = response.body().getData().getImageCode();
                } else {
                    // 处理响应失败情况
                    System.out.println("failed-1");
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<MyFile>> call, Throwable t) {
                // 处理网络请求失败情况
                System.out.println("failed-2");
            }
        });
        return result[0];
    }


    public static File getFileFromContentUri(Context context, Uri contentUri) {
        try {
            String fileName = getFileNameFromUri(context, contentUri);
            InputStream inputStream = context.getContentResolver().openInputStream(contentUri);
            File tempFile = new File(context.getCacheDir(), fileName);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getFileNameFromUri(Context context, Uri uri) {
        String fileName = null;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (displayNameIndex != -1) {
                    fileName = cursor.getString(displayNameIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (fileName == null) {
            fileName = "temp"; // Fallback name
        }
        return fileName;
    }




}
