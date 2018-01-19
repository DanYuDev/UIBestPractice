package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/1/19.
 */

public class TableData {
    private String title;
    private float num;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getNum() {
        return num;
    }

    public TableData(String title, float num) {
        this.title = title;
        this.num = num;
    }

    public TableData(){
        this.title="default";
        this.num=0.0f;
    }

    public void setNum(float num) {
        this.num = num;
    }
}
