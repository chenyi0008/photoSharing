package com.example.photosharing.api;

import com.example.photosharing.model.Comment;
import com.example.photosharing.model.CommentList;
import com.example.photosharing.model.MyFile;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.CommentDto;
import com.example.photosharing.model.dto.ImageShareDto;
import com.example.photosharing.model.dto.ImageShareListDto;
import com.example.photosharing.model.dto.UserInfoUpdateDto;

import java.util.List;

import okhttp3.MultipartBody;
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
     * @param fileList
     * @return
     */

    @POST("/member/photo/image/upload")
    @Multipart
    Call<ResponseBody<MyFile>> uploadFile(
            @Part List<MultipartBody.Part> fileList);


    /**
     * 修改用户信息
     * @param userInfoUpdateDto
     * @return
     */
    @POST("/member/photo/user/update")
    Call<ResponseBody> updateUser(@Body UserInfoUpdateDto userInfoUpdateDto);


    /**
     * 新增图文分享
     * @param dto
     * @return
     */
    @POST("/member/photo/share/add")
    Call<ResponseBody> ImageSharing(@Body ImageShareDto dto);

    /**
     * 获取图片分享发现列表
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param userId 当前登录用户主键id
     * @return
     */
    @GET("/member/photo/share")
    Call<ResponseBody<ImageShareListDto>> getSharingDiscoveries(@Query("current") int current,
                                                                @Query("size") int size,
                                                                @Query("userId") String userId);

    /**
     * 添加关注
     * @param focusUserId 被关注的用户id
     * @param userId  用户id
     * @return
     */
    @POST("/member/photo/focus")
    Call<ResponseBody> addFollow(@Query("focusUserId") String focusUserId,
                                     @Query("userId") String userId);


    /**
     * 取消关注
     * @param focusUserId 被关注的用户id
     * @param userId  用户id
     * @return
     */
    @POST("/member/photo/focus/cancel")
    Call<ResponseBody> unFollow(@Query("focusUserId") String focusUserId,
                                 @Query("userId") String userId);

    /**
     * 获取当前登录用户已关注的图文列表
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param userId 当前登录用户主键id
     * @return
     */
    @GET("/member/photo/focus")
    Call<ResponseBody<ImageShareListDto>> getFollowedImages(@Query("current") int current,
                                         @Query("size") int size,
                                         @Query("userId") String userId);

    /**
     * 获取我的动态图片分享列表
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param userId 当前登录用户主键id
     * @return
     */
    @GET("/member/photo/share/myself")
    Call<ResponseBody<ImageShareListDto>> getMyShare(@Query("current") int current,
                                                                @Query("size") int size,
                                                                @Query("userId") String userId);

    /**
     * 用户对图文分享进行收藏
     * @param shareId 图文分享的主键id
     * @param userId 当前登录用户主键id
     * @return
     */
    @POST("/member/photo/collect")
    Call<ResponseBody> collect(@Query("shareId") String shareId,
                               @Query("userId") String userId);

    /**
     * 用户取消对图文分享的收藏
     * collectId 收藏表主键id
     * @return
     */
    @POST("/member/photo/collect")
    Call<ResponseBody> unCollect(@Query("collectId") String collectId);

    /**
     * 获取当前登录用户收藏图文列表
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param userId 当前登录用户主键id
     * @return
     */
    @GET("/member/photo/collect")
    Call<ResponseBody<ImageShareListDto>> getMyCollect(@Query("current") int current,
                                                       @Query("size") int size,
                                                       @Query("userId") String userId);

    /**
     * 获取一级评论
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param shareId 图文分享的主键id
     * @return
     */
    @GET("/member/photo/comment/first")
    Call<ResponseBody<CommentList>> getFirstComment(@Query("current") int current,
                                                    @Query("size") int size,
                                                    @Query("shareId") String shareId);

    /**
     * 新增一个图片分享的一级评论
     * commentDto 评论封装类
     * @return
     */
    @POST("/member/photo/comment/first")
    Call<ResponseBody> setFirstComment(@Body CommentDto commentDto);


//    @POST("/member/photo/comment/second")

    /**
     * 用户对图文分享进行点赞
     * @param shareId 图文分享的主键id
     * @param userId 当前登录用户主键id
     * @return
     */
    @POST("/member/photo/like")
    Call<ResponseBody> like(@Query("shareId") String shareId,
                            @Query("userId") String userId);


    /**
     * 获取当前登录用户点赞图文列表
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param userId 当前登录用户主键id
     * @return
     */
    @GET("/member/photo/like")
    Call<ResponseBody<ImageShareListDto>> getLike(@Query("current") int current,
                                                @Query("size") int size,
                                                @Query("userId") String userId);


    /**
     * 用户取消对图文分享的点赞
     * @param likeId 点赞表主键id
     * @return
     */
    @POST("/member/photo/like/cancel")
    Call<ResponseBody> unLike(@Query("likeId") String likeId);


    /**
     * 获取已保存的图文分享列表
     * @param current 当前页 第一页写0
     * @param size 页面大小
     * @param userId 当前登录用户主键id
     * @return
     */
    @GET("/member/photo/share/save")
    Call<ResponseBody<ImageShareListDto>> getSave(@Query("current") int current,
                                                  @Query("size") int size,
                                                  @Query("userId") String userId);

    /**
     * 保存图文分享
     * @param dto
     * @return
     */
    @POST("/member/photo/share/save")
    Call<ResponseBody> saveImageSharing(@Body ImageShareDto dto);

























}
