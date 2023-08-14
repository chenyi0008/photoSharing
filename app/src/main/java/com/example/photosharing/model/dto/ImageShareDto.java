package com.example.photosharing.model.dto;

public class ImageShareDto {
    // 内容
    private String content;

    // 一组图片的唯一标识符【需要先通过获取】
    private String imageCode;

    // 当前登录用户(发布者)id
    private String pUserId;

    // 标题
    private String title;

    @Override
    public String toString() {
        return "ImageShareDto{" +
                "content='" + content + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", pUserId='" + pUserId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public ImageShareDto(String content, String imageCode, String pUserId, String title) {
        this.content = content;
        this.imageCode = imageCode;
        this.pUserId = pUserId;
        this.title = title;
    }

    public ImageShareDto() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getPUserId() {
        return pUserId;
    }

    public void setPUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
