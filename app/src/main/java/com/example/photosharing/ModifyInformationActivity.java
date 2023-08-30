package com.example.photosharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.MyFile;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.UserInfoUpdateDto;
import com.example.photosharing.util.ImageDownloader;
import com.example.photosharing.util.Uploader;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyInformationActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView userAvatar;
    private Spinner genderSpinner;
    private EditText introduceEditText;
    private TextView usernameTextView;

    private Uri uri;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyinformation);
        userAvatar = findViewById(R.id.userAvatar);
        genderSpinner = findViewById(R.id.genderGender);
        usernameTextView = findViewById(R.id.username);
        introduceEditText = findViewById(R.id.introduce);

        usernameTextView.setText(" " + UserInfo.getInstance().getUsername());
        boolean sex = UserInfo.getInstance().getSex() == null || UserInfo.getInstance().getSex().equals("0");


        String introduce = UserInfo.getInstance().getIntroduce();
        introduceEditText.setText(introduce == null ? "" : introduce);

        // 创建一个包含 "Male" 和 "Female" 选项的 ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 将 ArrayAdapter 设置到 Spinner 上
        genderSpinner.setAdapter(adapter);
        genderSpinner.setSelection(sex ? 0 : adapter.getPosition("Female"));

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
//        showToast(UserInfo.getInstance().getAvatar());
        String imgUrl = null;
        if(UserInfo.getInstance().getAvatar().isEmpty())
            imgUrl = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/26/f1e9df8b-f12e-4015-acc0-20e5b139636b.png";
        else imgUrl = UserInfo.getInstance().getAvatar();
//        Call<ResponseBody<UserInfo>> call2 = httpUtil.getUserByName(username);
        final Bitmap[] bitmap = {null};
        new ImageDownloader(userAvatar).execute(imgUrl);
        userAvatar.setImageBitmap(bitmap[0]);

    }

    public void onUploadImageClicked(View view) {
        flag = true;
        // 创建一个用于打开图库的意图
        Intent intent = new Intent(Intent.ACTION_PICK);

        // 设置意图的数据类型为图像类型
        intent.setType("image/*");

        // 启动带有图库选择的意图，并提供一个请求码
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    public void save(View view){
        String avatar;
        String introduction = introduceEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();







        if (introduction.isEmpty()) {
            showToast("your introduction is empty");
        } else {
            UserInfo instance = UserInfo.getInstance();
            instance.setIntroduce(introduction);
            instance.setSex(gender.equals("Female") ? "1" : "0");


            if(flag){

                ArrayList<File> fileList = new ArrayList<>();
                ArrayList<Uri> selectedImageUris = new ArrayList<>();
                selectedImageUris.add(uri);

                for (Uri imageUris : selectedImageUris) {
                    File file = Uploader.getFileFromContentUri(getBaseContext(), imageUris);
                    fileList.add(file);


                }
                RetrofitRequest_Interface r = MyRetrofit.getRetrofitRequestInterface();
                ArrayList<MultipartBody.Part> list = new ArrayList<>();
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
                            String imageCode = response.body().getData().getImageUrlList().get(0);
                            System.out.println("图片ImageCode:" + response.body().getData().getImageUrlList().get(0));


                            instance.setAvatar(imageCode);
                            UserInfoUpdateDto u = new UserInfoUpdateDto();
                            u.setAvatar(imageCode);
                            u.setUsername(UserInfo.getInstance().getUsername());
                            u.setId(UserInfo.getInstance().getId());
                            u.setIntroduce(introduction);//注意 不能传中文 传中文会报错
                            u.setSex(gender.equals("Male") ? "0" : "1");//sex=0时 为男性  sex=1时 为女性
                            RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                            Call<ResponseBody> call2 = httpUtil.updateUser(u);
                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        // 修改成功，处理响应
                                        System.out.println("请求成功");
                                        showToast("Successfully modified");

                                    } else {
                                        // 注册失败，处理错误情况
                                        System.out.println("请求失败");
                                        showToast("Modification failed");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    System.out.println("服务器异常");
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
                }else{
                    UserInfoUpdateDto u = new UserInfoUpdateDto();
                    u.setAvatar(UserInfo.getInstance().getAvatar());
                    u.setUsername(UserInfo.getInstance().getUsername());
                    u.setId(UserInfo.getInstance().getId());
                    u.setIntroduce(introduction);//注意 不能传中文 传中文会报错
                    u.setSex(gender.equals("Male") ? "0" : "1");//sex=0时 为男性  sex=1时 为女性
                    RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                    Call<ResponseBody> call2 = httpUtil.updateUser(u);
                    call2.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // 修改成功，处理响应
                                System.out.println("请求成功");
                                showToast("Successfully modified");

                            } else {
                                // 注册失败，处理错误情况
                                System.out.println("请求失败");
                                showToast("Modification failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println("服务器异常");
                        }
                    });
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 检查请求码和结果码，以及接收到的数据是否为空
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // 从意图中获取选中图像的URI
            Uri selectedImageUri = data.getData();
            uri = selectedImageUri;
            // 将选中的图像URI设置给用户头像ImageView，从而显示图像
            userAvatar.setImageURI(selectedImageUri);
        }
    }
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




}