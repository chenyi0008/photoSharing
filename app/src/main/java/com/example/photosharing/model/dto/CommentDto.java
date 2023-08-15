package com.example.photosharing.model.dto;

public class CommentDto {
    private String content;   // 评论的内容【新增成功后可到查询】
    private String shareId;   // 图文分享的主键id【可通过获取id】
    private String userId;    // 评论人userId
    private String userName;  // 评论人用户名

    @Override
    public String toString() {
        return "CommentDto{" +
                "\n    content='" + content + '\'' +
                ", \n    shareId='" + shareId + '\'' +
                ", \n    userId='" + userId + '\'' +
                ", \n    userName='" + userName + '\'' +
                "\n}";
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
