package com.example.photosharing.model.dto;


import java.util.List;

/**
 * 图文分享项实体类
 */
public class ImageShareItemDto {
    // 主键id
    private String id;

    // 当前登录用户(发布者)id
    private String pUserId;

    // 一组图片的唯一标识符
    private String imageCode;

    // 标题
    private String title;

    // 内容
    private String content;

    // 创建时间
    private String createTime;

    // 图片的list集合
    private List<String> imageUrlList;

    // 当前图片分享的用户点赞的主键id
    private String likeId;

    // 当前图片分享的点赞数
    private Integer likeNum;

    // 是否已点赞
    private Boolean hasLike;

    // 当前图片分享的用户收藏的主键id
    private String collectId;

    // 当前图片分享的收藏数
    private Integer collectNum;

    // 是否已收藏
    private Boolean hasCollect;

    // 是否已关注
    private Boolean hasFocus;

    // 当前登录用户名
    private String username;

    @Override
    public String toString() {
        return "ImageShareItemDto{" +
                "id='" + id + '\'' +
                ", pUserId='" + pUserId + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", imageUrlList=" + imageUrlList +
                ", likeId='" + likeId + '\'' +
                ", likeNum=" + likeNum +
                ", hasLike=" + hasLike +
                ", collectId='" + collectId + '\'' +
                ", collectNum=" + collectNum +
                ", hasCollect=" + hasCollect +
                ", hasFocus=" + hasFocus +
                ", username='" + username + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpUserId() {
        return pUserId;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Boolean getHasLike() {
        return hasLike;
    }

    public void setHasLike(Boolean hasLike) {
        this.hasLike = hasLike;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public Boolean getHasFocus() {
        return hasFocus;
    }

    public void setHasFocus(Boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
