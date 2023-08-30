package com.example.photosharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.fragment.ImmerseFragment;
import com.example.photosharing.fragment.PersonFragment;
import com.example.photosharing.fragment.TestFragment;
import com.example.photosharing.model.ImageAdapter;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.model.dto.ImageShareListDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreActivity extends AppCompatActivity {
    private int type;
    private List<ImageShareItemDto> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Intent intent = getIntent();
        type = (int) intent.getExtras().get("type");
        InitData();
    }

    private void InitData(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = UserInfo.getInstance().getId();

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call;
        switch (type){
            //收藏列表
            case 0:call = httpUtil.getMyCollect(current, size, userId);break;
            //点赞列表
            case 1:call = httpUtil.getLike(current, size, userId);break;
            //我的分享列表
            case 2:call = httpUtil.getMyShare(current, size, userId);break;
            //我的关注
            default: call = httpUtil.getFollowedImages(current, size, userId);break;
        }

        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    if(response.body().getData()!=null){
                        imageList=response.body().getData().getRecords();
                        UpdateView();
                    }
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

    private void UpdateView(){
        ImageAdapter imageAdapter = new ImageAdapter(MoreActivity.this,
                R.layout.list_view , imageList);
        ListView lvImageList =findViewById(R.id.lv_image_list);
        lvImageList.setAdapter(imageAdapter);
    }
}
