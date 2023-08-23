package com.example.photosharing.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.photosharing.model.dto.ImageShareItemDto;

import java.util.List;

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


    }
}
