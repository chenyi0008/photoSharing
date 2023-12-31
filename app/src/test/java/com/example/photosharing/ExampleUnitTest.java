package com.example.photosharing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.Comment;
import com.example.photosharing.model.CommentList;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.User;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.CommentDto;
import com.example.photosharing.model.dto.ImageShareDto;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.model.dto.ImageShareListDto;
import com.example.photosharing.model.dto.UserInfoUpdateDto;
import com.example.photosharing.util.Uploader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    CountDownLatch latch;
    @Before
    public void before(){
        latch = new CountDownLatch(1);
    }

    @After
    public void after() throws InterruptedException {
        latch.await(2, TimeUnit.SECONDS);
    }

    /**
     * 测试注册
     * @throws InterruptedException
     */
    @Test
    public void registerTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        User u = new User();
        u.setUsername("admin");
        u.setPassword("admin");

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.register(u);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败，处理错误情况");


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 处理网络请求失败
                System.out.println("处理网络请求失败");
            }
        });

        latch.await(2, TimeUnit.SECONDS);

    }



    /**
     * 测试登录
     * @throws InterruptedException
     */
    @Test
    public void loginTest() throws InterruptedException {


        String username = "admin";
        String password = "admin";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = httpUtil.login(username, password);

        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
                    System.out.println(response.body());

                    UserInfo userInfo = response.body().getData();
                    System.out.println(userInfo);
                    System.out.println(response.body().getMsg());

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

    /**
     * 上传文件(单个)
     */
    @Test
    public void uploadTest() throws InterruptedException {

        String filePath = "D:\\Desktop\\小豆泥表情合集\\-2b2a0513a122b71e.jpg";
        File file = new File(filePath);
        String s = Uploader.uploadImage(file);
        System.out.println(s);

    }

    /**
     * 上传文件(多个)
     */
    @Test
    public void uploadBatchTest() throws InterruptedException {

        String filePath1 = "D:\\Desktop\\小豆泥表情合集\\-2b2a0513a122b71e.jpg";
        File file1 = new File(filePath1);

        String filePath2 = "D:\\Desktop\\小豆泥表情合集\\7c6c38f1013f70b5.jpg";
        File file2 = new File(filePath2);

        ArrayList<File> fileList = new ArrayList<>();
        fileList.add(file1);
        fileList.add(file2);

        String s = Uploader.uploadImageBatch(fileList);
        System.out.println(s);

    }




    /**
     * 修改用户信息
     */
    @Test
    public void UserUpdateTest(){
        UserInfoUpdateDto u = new UserInfoUpdateDto();
        u.setAvatar("https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/22/37d6042f-7be1-4e69-b084-f56bc624767d.jpg");
        u.setUsername("admin");
        u.setId("1691822872735125504");
        u.setIntroduce("hello  everyone");//注意 不能传中文 传中文会报错
        u.setSex("1");//sex=1时 为男性  sex=0时 为女性

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.updateUser(u);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");


                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }


    /**
     * 新增图文分享
     */
    @Test
    public void imageShareTest(){
        ImageShareDto dto = new ImageShareDto();
        dto.setContent("content...");
        //注意 此处的ImageCode要从批量上传文件的api去获取
        dto.setImageCode("1690985100197629952");
        dto.setPUserId("1691822872735125504");
        dto.setTitle("title1");

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.ImageSharing(dto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }



    /**
     * 获取图片分享发现列表
     */
    @Test
    public void sharingDiscoveriesTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = "1691822872735125504";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getSharingDiscoveries(current, size, userId);

        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getData().toString());


                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareListDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }

    /**
     * 添加关注
     */
    @Test
    public void addFollowTest(){
        String focusUserId = "1690646693722329088";
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.addFollow(focusUserId, userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }


    /**
     * 取消关注
     */
    @Test
    public void unFollowTest(){
        String focusUserId = "1690646693722329088";
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.unFollow(focusUserId, userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }





    /**
     * 获取当前登录用户已关注的图文列表  problem
     */
    @Test
    public void getFollowedImagesTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getFollowedImages(current, size, userId);

        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getCode());
                    System.out.println(response.body().getMsg());
                    System.out.println(response.body().getData().toString());


                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareListDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }

    /**
     * 获取我的动态图片分享列表
     */
    @Test
    public void getMyShareTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getMyShare(current, size, userId);

        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getData().toString());


                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareListDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }

    /**
     * 用户对图文分享进行收藏
     */
    @Test
    public void collectTest(){
        String shareId = "4833";
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.collect(shareId, userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }


    /**
     * 用户取消对图文分享的收藏
     */
    @Test
    public void unCollectTest(){
        String collectId = "3236";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.unCollect(collectId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }



    /**
     * 获取当前登录用户收藏图文列表
     */
    @Test
    public void getMyCollectTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getMyCollect(current, size, userId);

        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());
                    System.out.println(response.body().getData().toString());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareListDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }

    /**
     * 新增一个图片分享的一级评论
     */
    @Test
    public void setFirstCommentTest(){
        CommentDto commentDto = new CommentDto();
        commentDto.setShareId("4833");
        commentDto.setUserId("1690980829649571840");
        commentDto.setUserName("chen");
        commentDto.setContent("hello");

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.setFirstComment(commentDto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }

    /**
     * 获取一级评论
     */
    @Test
    public void getFirstCommentTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String shareId = "4833";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<CommentList>> call = httpUtil.getFirstComment(current, size, shareId);
        System.out.println(call.request().url());
        call.enqueue(new Callback<ResponseBody<CommentList>>() {
            @Override
            public void onResponse(Call<ResponseBody<CommentList>> call, Response<ResponseBody<CommentList>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getData());


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

    /**
     * 用户对图文分享进行点赞
     */
    @Test
    public void likeTest(){
        String shareId = "4833";
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.like(shareId, userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });



    }


    /**
     * 获取当前登录用户点赞图文列表
     */
    @Test
    public void getLikeTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = "1690980829649571840";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getLike(current, size, userId);


        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getCode());
                    System.out.println(response.body().getMsg());
                    System.out.println(response.body().getData().toString());
                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareListDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });

    }

    /**
     * 用户取消对图文分享的点赞
     */
    @Test
    public void unLikeTest(){
        String likeId = "4833";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.unLike(likeId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }

    /**
     * 获取已保存的图文分享列表
     */
    @Test
    public void getSaveTest(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = "1690980829649571840";


        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getSave(current, size, userId);


        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getCode());
                    System.out.println(response.body().getMsg());
                    System.out.println(response.body().getData().toString());


                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<ImageShareListDto>> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }




    /**
     * 保存图文分享
     */
    @Test
    public void saveImageShareTest(){
        ImageShareDto dto = new ImageShareDto();
        dto.setContent("content...");
        //注意 此处的ImageCode要从批量上传文件的api去获取
        dto.setImageCode("1690985100197629952");
        dto.setPUserId("1690980829649571840");
        dto.setTitle("bilibili");

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody> call = httpUtil.saveImageSharing(dto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getMsg());

                } else {
                    // 注册失败，处理错误情况
                    System.out.println("请求失败");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("服务器异常");
            }
        });
    }


    /**
     * 获取单个图文分享的详情
     */
    @Test
    public void getShareByIdTest() throws IOException {
        String userId = "1691822872735125504";
        String shareId = "4877";

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

        ImageShareItemDto data = call.execute().body().getData();
        System.out.println(data.toString());
    }


    /**
     * 根据用户名获取用户信息
     * @throws InterruptedException
     */
    @Test
    public void getUserByNameTest() throws InterruptedException {


        String username = "admin";

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = httpUtil.getUserByName(username);

        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
                    System.out.println(response.body());

                    UserInfo userInfo = response.body().getData();
                    System.out.println(userInfo);
                    System.out.println(response.body().getMsg());

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