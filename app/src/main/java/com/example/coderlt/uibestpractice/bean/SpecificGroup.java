package com.example.coderlt.uibestpractice.bean;


/**
 * Created by coderlt on 2018/3/1.
 */

public class SpecificGroup {
    private String name;
    private int    iconId;
    private int    moneyType;  // 1 代表支出，0代表收入

    public static final int TYPE_OUT=0;
    public static final int TYPE_IN=1;

    public SpecificGroup(String name,int iconId, int moneyType){
        this.name = name;
        this.iconId = iconId;
        this.moneyType = moneyType;
    }

    public SpecificGroup(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    @Override
    public String toString() {
        return "SpecificGroup{" +
                "name='" + name + '\'' +
                ", iconId=" + iconId +
                ", moneyType=" + moneyType +
                '}';
    }
}
