package com.example.coderlt.uibestpractice.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.coderlt.uibestpractice.bean.Employee;
import com.example.coderlt.uibestpractice.bean.Goods;
import com.example.coderlt.uibestpractice.bean.Option;
import com.example.coderlt.uibestpractice.bean.Organization;
import com.example.coderlt.uibestpractice.bean.SalesRecord;
import com.example.coderlt.uibestpractice.bean.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/1/10.
 */

public class JsonUtils {
    public static final String TAG = "JSONUtils";

    /*
     不能通过返回值的形式返回 options，因为adapter里面的adapter只是fragment里面options的一个引用，
     即地址的引用，当fragment里面的options指向别的时候，这个options的改变就不会对观察者adapter里
     面的options造成任何影响。
     */
    public static void DealConfig(String responseText,List<Option> options) {
        try {
            //  List<Option> options=new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(responseText);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Option option = new Option();
                option.setConfig_id(object.getString("configuration_id"));
                option.setName(object.getString("configuration_name"));
                option.setImgUrl(object.getString("configuration_icon"));
                option.setUrl(object.getString("configuration_url"));
                options.add(option);
                Log.d("JsonUtils", option.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 处理进销存表单
    public static void DealSales(String responseText, List<SalesRecord> sales) {
        try{ // 这应该是非受查异常？？
            JSONArray jsonArray = JSONArray.parseArray(responseText);
            Goods goods = new Goods();

            for (int i=0;i<jsonArray.size();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                SalesRecord record = new SalesRecord();
                goods.setName(object.getString("goods_name"));
                goods.setPrice(object.getFloat("goods_price"));
                record.setGoods(goods);
                record.setCount(object.getInteger("goods_count"));

                sales.add(record);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // 处理部门及员工信息
    public static void DealSectionsInfo(String responseText, Organization organization){
        Log.d(TAG,responseText);
        try{
            JSONObject organizationObject = JSON.parseObject(responseText);
            organization.setNum(organizationObject.getInteger("branchs"));
            organization.setStatus(organizationObject.getString("status"));
            JSONArray sectionArray = organizationObject.getJSONArray("branch");
            List<Section> sections = new ArrayList<>();
            for(int i=0;i<sectionArray.size();i++){
                JSONObject sectionObject = sectionArray.getJSONObject(i);
                Section section = new Section();
                section.setSectionName(sectionObject.getString("sectionName"));
                JSONArray employeeArray = sectionObject.getJSONArray("list");
                List<Employee> employees = new ArrayList<>();
                for(int j=0;j<employeeArray.size();j++){
                    JSONObject employeeObject = employeeArray.getJSONObject(j);
                    Employee employee = new Employee();
                    employee.setName(employeeObject.getString("name"));
                    employee.setAge(employeeObject.getIntValue("age"));
                    employee.setJobGrade(employeeObject.getString("jobGrade"));
                    employee.setAvatarUrl(employeeObject.getString("avatar"));
                    employee.setPhoneNumber(employeeObject.getString("phoneNum"));
                    employees.add(employee);
                }
                section.setEmployees(employees);
                sections.add(section);
            }
            organization.setSectionList(sections);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
