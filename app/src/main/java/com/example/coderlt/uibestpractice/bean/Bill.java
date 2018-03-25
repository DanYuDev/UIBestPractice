package com.example.coderlt.uibestpractice.bean;

import java.util.Date;

/**
 * Created by coderlt on 2018/3/22.
 * 适用于 listView 的数据源，位于 记账本的明细 fragment 中
 */

public class Bill {
    /**
     * 账单的图标相关
     * SpecificGroup{name,iconId,isPay}
     */
    private SpecificGroup specificGroup;

    /**
     * noteText:添加的备注信息
     * 覆盖原有的 name
     */
    private String noteText;

    /**
     * notePath 增加的备注图片（只能是本地图片）
     */
    private String notePath;

    /**
     * 时间戳，用于归档和查询
     * listView的分隔等
     */

    private Date date;
    /**
     * 具体的金额，单位为元
     */
    private double amount;

    //---------------------------------

    /**
     * 时间戳内的支出合计
     */
    private float out;

    /**
     * 时间戳内的收入合计
     */
    private float in;

    /**
     * 这个是为了增加时间戳，新增的属性
     * 回头应该设计一个新的接口，可以按照某一个参数分类
     * 当ViewType是时间戳的时候，这个Bill实际上已经不是一个 Bill 了
     */
    private int viewType;

    /**
     * 两种 ViewType
     */
    public static final int TYPE_BILL=0;
    public static final int TYPE_TIME=1;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public float getOut() {
        return out;
    }

    public void setOut(float out) {
        this.out = out;
    }

    public float getIn() {
        return in;
    }

    public void setIn(float in) {
        this.in = in;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public SpecificGroup getSpecificGroup() {
        return specificGroup;
    }

    public void setSpecificGroup(SpecificGroup specificGroup) {
        this.specificGroup = specificGroup;
    }

    public String getNotePath() {
        return notePath;
    }

    public void setNotePath(String notePath) {
        this.notePath = notePath;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "specificGroup=" + specificGroup +
                ", noteText='" + noteText + '\'' +
                ", notePath='" + notePath + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", out=" + out +
                ", in=" + in +
                ", viewType=" + viewType +
                '}';
    }
}
