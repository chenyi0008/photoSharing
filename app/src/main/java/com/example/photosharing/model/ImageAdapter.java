package com.example.photosharing.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Debug;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photosharing.R;
import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.util.ImageDownloader;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageAdapter extends ArrayAdapter<ImageShareItemDto> {
    private List<ImageShareItemDto> mImageData;
    private Context mContext;
    private int resourceId;

    public ImageAdapter(Context context,int resourceId,List<ImageShareItemDto> data){
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

        ImageView image=view.findViewById(R.id.iv_image);
        TextView content=view.findViewById(R.id.tv_content);
        ImageView hasLike=view.findViewById(R.id.iv_like);
        TextView likeNum=view.findViewById(R.id.tv_likenum);
        ImageView hasCollect=view.findViewById(R.id.iv_collect);
        TextView collectNum=view.findViewById(R.id.tv_collectnum);
        final Bitmap[] bitmap = {null};
        new ImageDownloader(image).execute(imgItem.getImageUrlList().get(0));
        image.setImageBitmap(bitmap[0]);

        content.setText(imgItem.getContent());
        likeNum.setText(Integer.toString(imgItem.getLikeNum()));
        if(imgItem.getHasLike()){
            hasLike.setImageResource(R.drawable.ic_like_foreground);
        }
        else{
            hasLike.setImageResource(R.drawable.ic_unlike_foreground);
        }
        hasLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //点赞
                if (!imgItem.getHasLike()) {
                    String shareId = imgItem.getId();
                    String userId = UserInfo.getInstance().getId();

                    RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                    Call<ResponseBody> call = httpUtil.like(shareId, userId);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // 修改成功，处理响应
                                System.out.println("请求成功");
                                System.out.println(response.body().getMsg());
                                imgItem.setLikeNum(imgItem.getLikeNum()+1);
                                imgItem.setHasLike(true);
                                hasLike.setImageResource(R.drawable.ic_like_foreground);
                                likeNum.setText(Integer.toString(imgItem.getLikeNum()));
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
                else {
                    String likeId = imgItem.getLikeId();

                    RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                    Call<ResponseBody> call = httpUtil.unLike(likeId);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // 修改成功，处理响应
                                System.out.println("请求成功");
                                System.out.println(response.body().getMsg());
                                System.out.println("请求成功");
                                System.out.println(response.body().getMsg());
                                imgItem.setLikeNum(imgItem.getLikeNum()-1);
                                imgItem.setHasLike(false);
                                hasLike.setImageResource(R.drawable.ic_unlike_foreground);
                                likeNum.setText(Integer.toString(imgItem.getLikeNum()));
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
            }
        });

        collectNum.setText(Integer.toString(imgItem.getCollectNum()));
        if (imgItem.getHasCollect()){
            hasCollect.setImageResource(R.drawable.ic_collect_foreground);
        }
        else{
            hasCollect.setImageResource(R.drawable.ic_uncollect_foreground);
        }
        hasCollect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!imgItem.getHasCollect()){
                    String shareId = imgItem.getId();
                    String userId = UserInfo.getInstance().getId();

                    RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                    Call<ResponseBody> call = httpUtil.collect(shareId, userId);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // 修改成功，处理响应
                                System.out.println("请求成功");
                                System.out.println(response.body().getMsg());
                                imgItem.setHasCollect(true);
                                imgItem.setCollectNum(imgItem.getCollectNum()+1);
                                hasCollect.setImageResource(R.drawable.ic_collect_foreground);
                                collectNum.setText(Integer.toString(imgItem.getCollectNum()));
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
                else {
                    String collectId = imgItem.getCollectId();

                    RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
                    Call<ResponseBody> call = httpUtil.unCollect(collectId);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // 修改成功，处理响应
                                System.out.println("请求成功");
                                System.out.println(response.body().getMsg());
                                imgItem.setCollectNum(imgItem.getCollectNum()-1);
                                imgItem.setHasCollect(false);
                                hasCollect.setImageResource(R.drawable.ic_uncollect_foreground);
                                collectNum.setText(Integer.toString(imgItem.getCollectNum()));
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
            }
        });

        return view;
    }
}