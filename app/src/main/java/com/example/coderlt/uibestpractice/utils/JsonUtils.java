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

    /*
     不能通过返回值的形式返回 options，因为adapter里面的adapter只是fragment里面options的一个引用，
     即地址的引用，当fragment里面的options指向别的时候，这个options的改变就不会对观察者adapter里
     面的options造成任何影响。
     */
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
