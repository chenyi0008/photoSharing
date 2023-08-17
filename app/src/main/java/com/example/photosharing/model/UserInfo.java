package com.example.photosharing.model;

public class UserInfo {
    private static UserInfo instance;

    private String id; // 主键id
    private String appKey; // 用户创建应用的id
    private String username; // 用户名
    private String password; // 密码
    private String sex; // 性别
    private String introduce; // 个人介绍
    private String avatar; // 头像
    private String createTime; // 创建时间
    private String lastUpdateTime; // 修改时间

    public static synchronized UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }
    public static void SetInstance(UserInfo userInfo){
        instance=userInfo;
    }

    private UserInfo(){}

    public UserInfo(String id, String appKey, String username, String password, String sex,
                    String introduce, String avatar, String createTime, String lastUpdateTime) {
        this.id = id;
        this.appKey = appKey;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.introduce = introduce;
        this.avatar = avatar;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    // Getters and setters for each field

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", appKey='" + appKey + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", introduce='" + introduce + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}
