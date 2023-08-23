package com.example.photosharing.util;

import com.example.photosharing.model.UserInfo;

public class TmpData {

    private static TmpData instance;

    public static synchronized TmpData getInstance() {
        if (instance == null) {
            instance = new TmpData();
        }
        return instance;
    }
    public static void setInstance(TmpData userInfo){
        instance=userInfo;
    }

    String detailId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
