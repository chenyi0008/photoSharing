package com.example.photosharing.model;

import java.util.Date;

public class Comment {
    private String id;                  // 主键id
    private String appKey;              // 所申请的应用key
    private String pUserId;             // 评论人userId
    private String userName;            // 评论人用户名
    private String shareId;             // 图文分享的主键id
    private String parentCommentId;     // 父评论id
    private String parentCommentUserId; // 父评论的用户id
    private String replyCommentId;      // 被回复的评论id
    private String replyCommentUserId;  // 被回复的评论用户id
    private int commentLevel;           // 评论等级[1 一级评论 默认，2 二级评论]
    private String content;             // 评论的内容
    private int status;                 // 评论状态
    private int praiseNum;              // 点赞数
    private int topStatus;              // 置顶状态
    private String createTime;            // 创建时间

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "\n\tid='" + id + '\'' +
                ", \n\tappKey='" + appKey + '\'' +
                ", \n\tpUserId='" + pUserId + '\'' +
                ", \n\tuserName='" + userName + '\'' +
                ", \n\tshareId='" + shareId + '\'' +
                ", \n\tparentCommentId='" + parentCommentId + '\'' +
                ", \n\tparentCommentUserId='" + parentCommentUserId + '\'' +
                ", \n\treplyCommentId='" + replyCommentId + '\'' +
                ", \n\treplyCommentUserId='" + replyCommentUserId + '\'' +
                ", \n\tcommentLevel=" + commentLevel +
                ", \n\tcontent='" + content + '\'' +
                ", \n\tstatus=" + status +
                ", \n\tpraiseNum=" + praiseNum +
                ", \n\ttopStatus=" + topStatus +
                ", \n\tcreateTime=" + createTime +
                "\n}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getpUserId() {
        return pUserId;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getParentCommentUserId() {
        return parentCommentUserId;
    }

    public void setParentCommentUserId(String parentCommentUserId) {
        this.parentCommentUserId = parentCommentUserId;
    }

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public String getReplyCommentUserId() {
        return replyCommentUserId;
    }

    public void setReplyCommentUserId(String replyCommentUserId) {
        this.replyCommentUserId = replyCommentUserId;
    }

    public int getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(int commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getTopStatus() {
        return topStatus;
    }

    public void setTopStatus(int topStatus) {
        this.topStatus = topStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
