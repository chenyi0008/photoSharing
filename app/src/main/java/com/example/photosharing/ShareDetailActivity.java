package com.example.photosharing;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photosharing.Adapter.CommentAdapter;
import com.example.photosharing.Adapter.ImageAdapter;
import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.Comment;
import com.example.photosharing.model.CommentList;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.ShareDetailItem;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.CommentDto;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.util.ImageDownloader;
import com.example.photosharing.util.Uploader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareDetailActivity extends AppCompatActivity {

    private RecyclerView imagesRecyclerView, commentView;
    private TextView usernameTextView, contentTextView, titleTextView;

    private ImageView userAvatar;

    private String username = "";

    private EditText commentEditText;
    private Button submitCommentButton;

    private String _shareId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);

        imagesRecyclerView = findViewById(R.id.imagesRecyclerView);
        usernameTextView = findViewById(R.id.usernameTextView);
        contentTextView = findViewById(R.id.contentTextView);
        titleTextView = findViewById(R.id.titleTextView);
        userAvatar = findViewById(R.id.userAvatar);
        commentEditText = findViewById(R.id.commentEditText);
        submitCommentButton = findViewById(R.id.submitCommentButton);

        Intent intent = getIntent();
        if (intent != null) {
            ShareDetailItem item = intent.getParcelableExtra("friend_circle_item");
            if (item != null) {
                bindData(item);
            }
        }


        commentView = findViewById(R.id.commentsRecyclerView);
        commentView.setLayoutManager(new LinearLayoutManager(this));




    }



    private void bindData(ShareDetailItem item) {
        String shareId = item.getShareId();
        String userId = item.getUserId();
        _shareId = shareId;
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
                    usernameTextView.setText("author: "+ imageShareItemDto.getUsername());
                    contentTextView.setText("content: "+ imageShareItemDto.getContent());
                    titleTextView.setText("title: " + imageShareItemDto.getTitle());
                    username = imageShareItemDto.getUsername();


                    Call<ResponseBody<UserInfo>> call2 = httpUtil.getUserByName(username);
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



        Call<ResponseBody<CommentList>> call3 = httpUtil.getFirstComment(0, Integer.MAX_VALUE, shareId);
        call3.enqueue(new Callback<ResponseBody<CommentList>>() {
            @Override
            public void onResponse(Call<ResponseBody<CommentList>> call, Response<ResponseBody<CommentList>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getData());
                    CommentAdapter commentAdapter = new CommentAdapter(response.body().getData().getRecords());
                    commentView.setAdapter(commentAdapter);
                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<CommentList>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });



        submitCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentEditText.getText().toString().trim();
                if (!comment.isEmpty()) {
                    // TODO: Perform action to submit the comment to the server
                    CommentDto commentDto = new CommentDto();
                    commentDto.setShareId(shareId);
                    commentDto.setUserId(userId);
                    commentDto.setUserName(UserInfo.getInstance().getUsername());
                    commentDto.setContent(String.valueOf(commentEditText.getText()));

                    Call<ResponseBody> call = httpUtil.setFirstComment(commentDto);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // 修改成功，处理响应
                                System.out.println("请求成功");
                                System.out.println(response.body().getMsg());
                                Toast.makeText(ShareDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                refreshComment();

                            } else {
                                // 注册失败，处理错误情况
                                Toast.makeText(ShareDetailActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println("服务器异常");
                        }
                    });







                    commentEditText.setText("");
//                    showCommentSuccess();
                } else {
                    Toast.makeText(ShareDetailActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public List<Comment> getComments(){
        List<Comment> comments = new ArrayList<>();

        Comment comment1 = new Comment();
        comment1.setContent("This is a great post!");
        comment1.setUserName("Jack");
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("Thanks for sharing.");
        comment2.setUserName("Chen");
        comments.add(comment2);

        Comment comment3 = new Comment();
        comment3.setContent("Hello world.");
        comment3.setUserName("Jerry");
        comments.add(comment3);

        Comment comment4 = new Comment();
        comment4.setContent("Nice work!");
        comment4.setUserName("Emily");
        comments.add(comment4);

        Comment comment5 = new Comment();
        comment5.setContent("I enjoyed reading this.");
        comment5.setUserName("Alice");
        comments.add(comment5);

        Comment comment6 = new Comment();
        comment6.setContent("Keep it up!");
        comment6.setUserName("Alex");
        comments.add(comment6);

        Comment comment7 = new Comment();
        comment7.setContent("Impressive.");
        comment7.setUserName("Grace");
        comments.add(comment7);

        Comment comment8 = new Comment();
        comment8.setContent("You've got my vote.");
        comment8.setUserName("Ryan");
        comments.add(comment8);

        Comment comment9 = new Comment();
        comment9.setContent("Excellent content!");
        comment9.setUserName("Sophia");
        comments.add(comment9);

        Comment comment10 = new Comment();
        comment10.setContent("Very informative.");
        comment10.setUserName("Oliver");
        comments.add(comment10);

        return comments;


    }

    public void refreshComment(){
        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<CommentList>> call3 = httpUtil.getFirstComment(0, Integer.MAX_VALUE, _shareId);
        call3.enqueue(new Callback<ResponseBody<CommentList>>() {
            @Override
            public void onResponse(Call<ResponseBody<CommentList>> call, Response<ResponseBody<CommentList>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getData());
                    CommentAdapter commentAdapter = new CommentAdapter(response.body().getData().getRecords());
                    commentView.setAdapter(commentAdapter);
                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<CommentList>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });


    }

}
