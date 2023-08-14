package com.example.photosharing.api;

import com.example.photosharing.model.MyFile;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.UserInfoUpdateDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitRequest_Interface {

    /**
     * 创建用户
     * @param user
     * @return
     */
    @POST("/member/photo/user/register")
    Call<ResponseBody> register(@Body User user);

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    @POST("/member/photo/user/login")
    Call<ResponseBody<UserInfo>> login(@Query("username") String username,
                                       @Query("password") String password);


    /**
     * 上传文件
     * @param file
     * @return
     */

//    @POST("/member/photo/image/upload")
    @Multipart
    @POST("/files/uploadOne")
    Call<ResponseBody<MyFile>> uploadFile(
            @Part MultipartBody.Part file);



    @POST("/member/photo/user/update")
    Call<ResponseBody> updateUser(@Body UserInfoUpdateDto userInfoUpdateDto);



}
