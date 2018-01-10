package com.example.coderlt.uibestpractice.utils;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.coderlt.uibestpractice.bean.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/1/10.
 */

public class JsonUtils {
    public final String TAG=getClass().getName();
    public static void DealConfig(String responseText,List<Option> options){
        //List<Option> options=new ArrayList<>();
        JSONArray jsonArray= JSONArray.parseArray(responseText);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            Option option=new Option();
            option.setConfig_id(object.getString("configuration_id"));
            option.setName(object.getString("configuration_name"));
            option.setImgUrl(object.getString("configuration_icon"));
            option.setUrl(object.getString("configuration_url"));
            options.add(option);
            Log.d("JsonUtils",option.toString());
        }
    }
}
