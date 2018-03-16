package com.example.coderlt.uibestpractice.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coderlt on 2018/3/15.
 */

public class Organization implements Serializable{
    // 状态
    private String status;

    // 部门数
    private int num;

    // 部门分支
    private List<Section> sectionList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName()+":{\n"+
                "status:"+status+"\n"+
                "num:"+num+"\n"+
                "sectionList:"+sectionList+"}";
    }
}
