package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/1/4.
 */

public class User {

    public static class LOGIN_LEVEL{
        private static final int BOSS=0;
        private static final int MANAGER=1;
        private static final int SERVER=2;
        private static final int CUSTOMER=3;
    }

    private String userName;  //昵称
    private String userId;    //主键
    private String userPhone; //手机号，目前是唯一注册途径
    private String account;   //账号，目前对应的就是手机号
    private String userToken; //客户端 token，后台请求凭据
    private String credit;    //系统积分
    private int    auth;      //登陆权限，对应

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }
}
