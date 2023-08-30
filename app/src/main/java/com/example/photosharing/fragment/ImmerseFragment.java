package com.example.photosharing.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.photosharing.R;
import com.example.photosharing.ShareActivity;
import com.example.photosharing.api.MyRetrofit;
import com.example.photosharing.api.RetrofitRequest_Interface;
import com.example.photosharing.model.ImageAdapter;
import com.example.photosharing.model.ResponseBody;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.model.dto.ImageShareItemDto;
import com.example.photosharing.model.dto.ImageShareListDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImmerseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImmerseFragment extends Fragment {
    List<ImageShareItemDto> imageList=new ArrayList<ImageShareItemDto>();
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ImmerseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImmerseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImmerseFragment newInstance(String param1, String param2) {
        ImmerseFragment fragment = new ImmerseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        InitData();
    }

    private void InitData(){
        int current = 0; //第0页
        int size = 10;  //大小为10
        String userId = UserInfo.getInstance().getId();

        RetrofitRequest_Interface httpUtil = MyRetrofit.getRetrofitRequestInterface();
        Call<ResponseBody<ImageShareListDto>> call = httpUtil.getSharingDiscoveries(current, size, userId);

        call.enqueue(new Callback<ResponseBody<ImageShareListDto>>() {
            @Override
            public void onResponse(Call<ResponseBody<ImageShareListDto>> call, Response<ResponseBody<ImageShareListDto>> response) {
                if (response.isSuccessful()) {
                    // 修改成功，处理响应
                    System.out.println("请求成功");
                    System.out.println(response.body().getData());
                    imageList=response.body().getData().getRecords();
                    UpdateView(view);
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_immerse, container, false);

        view.findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShareActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void UpdateView(View view){
        ImageAdapter imageAdapter = new ImageAdapter(getActivity() ,
                R.layout.list_view , imageList);
        ListView lvImageList =view.findViewById(R.id.lv_image_list);
        lvImageList.setAdapter(imageAdapter);
    }
}