package com.example.photosharing.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photosharing.R;
import com.example.photosharing.ShareDetailActivity;
import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.ShareDetailItem;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.util.ImageDownloader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDraftAdapter extends ArrayAdapter<ImageShareItemDto> {
    private List<ImageShareItemDto> mImageData;
    private Context mContext;
    private int resourceId;

    ImageView image;
    ImageView userAvatar;
    TextView name;
    TextView title;
    TextView content;

    public ImageDraftAdapter(Context context, int resourceId, List<ImageShareItemDto> data){
        super(context,resourceId,data);
        this.mContext=context;
        this.resourceId=resourceId;
        this.mImageData=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ImageShareItemDto imgItem=getItem(position);
        View view;
        view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        view.findViewById(R.id.shareitem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), ShareDetailActivity.class);

                String username = imgItem.getUsername();
                String timestamp = imgItem.getCreateTime();
                String content = imgItem.getContent();
                List<String> images = imgItem.getImageUrlList();

                String shareId = imgItem.getId();
                String userId = imgItem.getpUserId();
                ShareDetailItem item = new ShareDetailItem(shareId, userId);

                // 将 ShareDetailItem 对象传递给分享详情页面
                intent.putExtra("friend_circle_item", item);

                // 启动分享详情页面
                parent.getContext().startActivity(intent);
            }
        });

         image=view.findViewById(R.id.iv_image);
         userAvatar=view.findViewById(R.id.user_draft_avatar);
         name=view.findViewById(R.id.tv_name);
         title=view.findViewById(R.id.tv_title);
         content=view.findViewById(R.id.tv_content);


        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<UserInfo>> call = httpUtil.getUserByName(imgItem.getUsername());
        call.enqueue(new Callback<ResponseBody<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseBody<UserInfo>> call, Response<ResponseBody<UserInfo>> response) {
                if (response.isSuccessful()) {
                    // 注册成功，处理响应
                    System.out.println("请求成功，处理响应");
                    if(response.body().getData()!=null){
                        String avatar = response.body().getData().getAvatar();
                        if(avatar == null || avatar.isEmpty()){
                            avatar ="https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/26/f1e9df8b-f12e-4015-acc0-20e5b139636b.png";
                        }

                        new ImageDownloader(userAvatar).execute(avatar);
                        System.out.println("下载图片成功");
                    }
                } else {
                    // 注册失败，处理错误情况
                    System.out.println("下载图片失败");
                    System.out.println(call.request().url());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody<UserInfo>> call, Throwable t) {
                System.out.println("失败");
            }
        });
        new ImageDownloader(image).execute(imgItem.getImageUrlList().get(0));

        name.setText(imgItem.getUsername());
        title.setText(imgItem.getTitle());
        content.setText(imgItem.getContent());
        System.out.println(imgItem.getUsername());
        System.out.println(imgItem.getTitle());
        System.out.println(imgItem.getContent());

        return view;
    }
}