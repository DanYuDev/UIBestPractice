package com.example.coderlt.uibestpractice.utils;

import okhttp3.MediaType;

/**
 * Created by coderlt on 2018/1/4.
 */

public class Constant {
    public static final String USER_PREF_NAME="user_info";

    public static final int LOGIN_BY_VERIFICATION=0x111;
    public static final int LOGIN_BY_PASSWORD=0x222;

    public static final String LOGIN_URL = "http://192.168.125.81:8080/HangPaiSoftCamp/admin/login.action";
    public static final String REG_URL= "http://192.168.125.81:8080/HangPaiSoftCamp/addMerchant.action";
    public static final String CHECK_PHONE_URL = "http://localhost:8080/HangPaiSoftCamp/checkPhone.action";
    public static final String CONFIG_URL = "http://192.168.125.81:8080/HangPaiSoftCamp/selectConfiguration.action";
    public static final String SALES_URL = "https://www.baidu.co";

    public static MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static class USER {

        public static final String USER_NAME = "user_name";  //昵称
        public static final String USER_ID = "user_id";    //主键
        public static final String USER_PHONE = "user_phone"; //手机号，目前是唯一注册途径
        public static final String USER_ACCOUNT = "user_account";   //账号，目前对应的就是手机号
        public static final String USER_CREDIT="user_credit";    //系统积分
        public static final String USER_AUTH="user_auth";      //登陆权限，对应
        public static final String USER_ADDRESS="user_address";
    }
}
