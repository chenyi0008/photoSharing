package com.example.photosharing.model;

import java.util.List;

public class MyFile {
    private String imageCode;
    private List<String> imageUrlList;

    public MyFile(String imageCode, List<String> imageUrlList) {
        this.imageCode = imageCode;
        this.imageUrlList = imageUrlList;
    }

    public String getImageCode() {
        return imageCode;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }
}
