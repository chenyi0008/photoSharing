package com.example.photosharing;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photosharing.Adapter.ImageAdapter;
import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.ShareDetailItem;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.util.ImageDownloader;
import com.example.photosharing.util.Uploader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareDetailActivity extends AppCompatActivity {

    private RecyclerView imagesRecyclerView;
    private TextView usernameTextView, contentTextView, titleTextView;

    private ImageView userAvatar;

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);

        imagesRecyclerView = findViewById(R.id.imagesRecyclerView);
        usernameTextView = findViewById(R.id.usernameTextView);
        contentTextView = findViewById(R.id.contentTextView);
        titleTextView = findViewById(R.id.titleTextView);
        userAvatar = findViewById(R.id.userAvatar);
        Intent intent = getIntent();
        if (intent != null) {
            ShareDetailItem item = intent.getParcelableExtra("friend_circle_item");
            if (item != null) {
                bindData(item);

//                getAvatar();
            }
        }




    }

    private void bindData(ShareDetailItem item) {
        String shareId = item.getShareId();
        String userId = item.getUserId();

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareItemDto>> call = httpUtil.getShareById(shareId, userId);


        call.enqueue(new Callback<ResponseBody<ImageShareItemDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareItemDto>> call, Response<ResponseBody<ImageShareItemDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());
                    System.out.println(response.body().getData().toString());
                    ImageShareItemDto imageShareItemDto = response.body().getData();
                    usernameTextView.setText("username: "+ imageShareItemDto.getUsername());
                    contentTextView.setText("content: "+ imageShareItemDto.getContent());
                    titleTextView.setText("title: " + imageShareItemDto.getTitle());
                    username = imageShareItemDto.getUsername();


                    Call<ResponseBody<UserInfo>> call2 = httpUtil.getUserByName(username);
                    System.out.println(">????????");
                    System.out.println(username);
                    call2.enqueue(new Callback<ResponseBody<UserInfo>>() {
                        @Override
                        public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                            if (response.isSuccessful()) {
                                // 注册成功，处理响应
                                System.out.println("请求成功，处理响应");
                                final Bitmap[] bitmap = {null};
                                new ImageDownloader(userAvatar).execute(response.body().getData().getAvatar());
                                userAvatar.setImageBitmap(bitmap[0]);

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







                    List<String> imageUrls = imageShareItemDto.getImageUrlList();
                    if (imageUrls != null && !imageUrls.isEmpty()) {
                        ImageAdapter imageAdapter = new ImageAdapter(imageUrls, getBaseContext());
                        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
                        imagesRecyclerView.setAdapter(imageAdapter);
                    }

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareItemDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }


    private void getAvatar(){
        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = httpUtil.getUserByName(username);
        System.out.println(">????????");
        System.out.println(username);
        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
                    final Bitmap[] bitmap = {null};
                    new ImageDownloader(userAvatar).execute(response.body().getData().getAvatar());
                    userAvatar.setImageBitmap(bitmap[0]);

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



    }
}
