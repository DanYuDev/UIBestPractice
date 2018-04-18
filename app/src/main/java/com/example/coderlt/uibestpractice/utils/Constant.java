package com.example.coderlt.uibestpractice.utils;

import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;

import okhttp3.MediaType;

/**
 * Created by coderlt on 2018/1/4.
 */

public class Constant {
    public static final String USER_PREF_NAME="user_info";

    public static final int LOGIN_BY_VERIFICATION=0x111;
    public static final int LOGIN_BY_PASSWORD=0x222;
    public static final int LOG_IN=0x333;
    public static final int LOG_OUT=0x444;

    public static final String LOGIN_URL = "http://192.168.125.81:8080/HangPaiSoftCamp/login/admin";
    public static final String REG_URL= "http://192.168.125.99:8080/HangPaiSoftCamp/admin/middle/add";
    public static final String CHECK_PHONE_URL = "http://localhost:8080/HangPaiSoftCamp/checkPhone.action";
    public static final String USER_CONFIG_URL = "http://192.168.125.81:8080/HangPaiSoftCamp/configuration/select/user";
    public static final String HOME_CONFIG_URL = "http://192.168.125.81:8080/HangPaiSoftCamp/configuration/select/home";
    public static final String SALES_URL = "https://www.baidu.co";
    public static final String SHOP_URL = "https://www.baidu.com";

    public static MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static class LOGIN_STATUS{
        public static final String APP_TOKEN = "app_token";
    }

    public static class SHOP{
        public static final String SHOP_ID = "shop_id";
        public static final String BUILD_SHOP_URL = "http://192.168.125.81:8080/HangPaiSoftCamp/admin/merchant/add";
        public static final String SHOP_MANAGER = "shop_manager";
        public static final String SHOP_NAME = "shop_name";
    }
    public static class USER {
        public static final String USER_NAME = "user_name";  //昵称
        public static final String USER_ID = "user_id";    //主键
        public static final String USER_PHONE = "user_phone"; //手机号，目前是唯一注册途径
        public static final String USER_ACCOUNT = "user_account";   //账号，目前对应的就是手机号
        public static final String USER_CREDIT="user_credit";    //系统积分
        public static final String USER_AUTH="user_auth";      //登陆权限，对应
        public static final String USER_ADDRESS="user_address";
        public static final String USER_STATUS = "user_status";
    }

    public static class BILL{
        // 和数据库的名称保持一致
        public static final String NAME = "bill_name";
        public static final String ICON = "bill_icon";
        public static final String NOTETEXT = "bill_noteText";
        public static final String NOTEPATH = "bill_noteUrl";
        public static final String TYPE = "bill_type";
        public static final String MONEY = "bill_money";
        public static final String DATE = "bill_date";
    }

    // emulate data

    public static final String organizationJSON = MyApplication.getContext().getResources()
                .getString(R.string.organization_json);
}
