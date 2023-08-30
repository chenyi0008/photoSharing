package com.example.photosharing.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ShareDetailItem implements Parcelable {
    private String shareId;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ShareDetailItem(String shareId, String userId) {
        this.shareId = shareId;
        this.userId = userId;
    }
    protected ShareDetailItem(Parcel in) {
        shareId = in.readString();
        userId = in.readString();
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public static final Creator<ShareDetailItem> CREATOR = new Creator<ShareDetailItem>() {
        @Override
        public ShareDetailItem createFromParcel(Parcel in) {
            return new ShareDetailItem(in);
        }

        @Override
        public ShareDetailItem[] newArray(int size) {
            return new ShareDetailItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shareId);
        dest.writeString(userId);
    }
}
