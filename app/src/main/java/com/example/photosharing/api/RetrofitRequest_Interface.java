package com.example.photosharing.api;

import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitRequest_Interface {

    @POST("/member/photo/user/register")
    Call<ResponseBody> register(@Body User user);





}
