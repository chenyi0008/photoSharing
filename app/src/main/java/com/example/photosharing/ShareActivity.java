package com.example.photosharing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photosharing.adapter.SelectedImagesAdapter;
import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.MyFile;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.ImageShareDto;
import com.example.photosharing.util.Uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button submitButton, uploadButton, saveButton, getDraftButton;

    private static final int PICK_IMAGE_REQUEST = 2;

    private SelectedImagesAdapter adapter;

    private List<Uri> selectedImageUris = new ArrayList<>();

    private RecyclerView selectedImagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_share);

        // 初始化界面元素
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        submitButton = findViewById(R.id.submitCommentButton);
        uploadButton = findViewById(R.id.uploadImgButton);
        saveButton = findViewById(R.id.saveCommentButton);
        getDraftButton = findViewById(R.id.getDraftButton);
        uploadButton.setOnClickListener(view -> openImagePicker());
        getDraftButton = findViewById(R.id.getDraftButton);

        getDraftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShareActivity.this, MoreActivity.class);
                intent.putExtra("type", 4);
                startActivity(intent);
            }
        });

        // 处理分享按钮点击事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                if(title.isEmpty()){
                    showToast("Your title cannot be empty");
                    return;
                }
                if(content.isEmpty()){
                    showToast("Your content cannot be empty");
                    return;
                }
                if(selectedImageUris.size() == 0){
                    showToast("Please upload the image");
                }

                //上传图片
                ArrayList<File> fileList = new ArrayList<>();
                for (Uri imageUris : selectedImageUris) {
                    File file = Uploader.getFileFromContentUri(getBaseContext(), imageUris);
                    fileList.add(file);
                }
                RetrofitRequest_Interface r = MyRetrofit.getRetrofitRequestInterface();
                ArrayList<MultipartBody.Part> list = new ArrayList<>();
                String imageCode = Uploader.uploadImageBatch(fileList);
                for (File file : fileList) {
                    RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("fileList", file.getName(), fileRequestBody);
                    list.add(filePart);
                }
                Call<ResponseBody<MyFile>> call = r.uploadFile(list);
                call.enqueue(new Callback<ResponseBody<MyFile>>() {

                    @Override
                    public void onResponse(Call<ResponseBody<MyFile>> call, Response<ResponseBody<MyFile>> response) {
                        if (response.isSuccessful()) {
                            String imageCode = response.body().getData().getImageCode();
                            System.out.println("图片ImageCode:" + response.body().getData().getImageCode());



                            ImageShareDto dto = new ImageShareDto();
                            dto.setContent(content);
                            dto.setImageCode(imageCode);
                            dto.setPUserId(UserInfo.getInstance().getId());
                            dto.setTitle(title);
                            System.out.println(dto.toString());
                            RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                            Call<ResponseBody> call2 = httpUtil.ImageSharing(dto);

                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        showToast("Successfully shared");


                                    } else {
                                        showToast(response.body().getMsg());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    showToast("error");
                                }
                            });











                        } else {
                            // 处理响应失败情况
                            System.out.println("failed-1");
                            System.out.println(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody<MyFile>> call, Throwable t) {
                        // 处理网络请求失败情况
                        System.out.println("failed-2 上传图片");
                    }
                });









            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                if(title.isEmpty()){
                    showToast("Your title cannot be empty");
                    return;
                }
                if(content.isEmpty()){
                    showToast("Your content cannot be empty");
                    return;
                }
                if(selectedImageUris.size() == 0){
                    showToast("Please upload the image");
                }

                //上传图片
                ArrayList<File> fileList = new ArrayList<>();
                for (Uri imageUris : selectedImageUris) {
                    File file = Uploader.getFileFromContentUri(getBaseContext(), imageUris);
                    fileList.add(file);
                }
                RetrofitRequest_Interface r = MyRetrofit.getRetrofitRequestInterface();
                ArrayList<MultipartBody.Part> list = new ArrayList<>();
                String imageCode = Uploader.uploadImageBatch(fileList);
                for (File file : fileList) {
                    RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("fileList", file.getName(), fileRequestBody);
                    list.add(filePart);
                }
                Call<ResponseBody<MyFile>> call = r.uploadFile(list);
                call.enqueue(new Callback<ResponseBody<MyFile>>() {

                    @Override
                    public void onResponse(Call<ResponseBody<MyFile>> call, Response<ResponseBody<MyFile>> response) {
                        if (response.isSuccessful()) {
                            String imageCode = response.body().getData().getImageCode();
                            System.out.println("图片ImageCode:" + response.body().getData().getImageCode());





                            ImageShareDto dto = new ImageShareDto();
                            dto.setContent(content);
                            dto.setImageCode(imageCode);
                            dto.setPUserId(UserInfo.getInstance().getId());
                            dto.setTitle(title);
                            System.out.println(dto.toString());
                            RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                            Call<ResponseBody> call2 = httpUtil.saveImageSharing(dto);

                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        showToast("Successfully saved");


                                    } else {
                                        showToast(response.body().getMsg());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    showToast("error");
                                }
                            });











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









            }
        });

        // 初始化图片展示的RecyclerView
        selectedImagesRecyclerView = findViewById(R.id.selectedImagesRecyclerView);
        selectedImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectedImagesAdapter(selectedImageUris, this);
        selectedImagesRecyclerView.setAdapter(adapter);
    }



    // 显示Toast消息
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // 打开图片选择器
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        selectedImageUris.add(imageUri);
                    }
                    adapter.notifyDataSetChanged();
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    selectedImageUris.add(imageUri);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
