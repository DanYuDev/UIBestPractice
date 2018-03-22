package com.example.coderlt.uibestpractice.bean;

import java.util.Date;

/**
 * Created by coderlt on 2018/3/22.
 * 适用于 listView 的数据源，位于 记账本的明细 fragment 中
 */

public class Bill {
    /**
     * name就是这笔账款的用途，有一个和图标匹配的默认值
     * 用户填写的备注可以覆盖默认值
     */
    private String name;
    /**
     * 时间戳，用于归档和查询
     * listView的分隔等
     */
    private Date date;
    /**
     * 具体的金额，单位为元
     */
    private float amount;

    /**
     * true 代表这笔账单是支出
     * false 代表这笔账单是收入
     */
    private boolean isPay;

    /**
     * 这个是为了增加时间戳，新增的属性
     * 回头应该设计一个新的接口，可以按照某一个参数分类
     * 当ViewType是时间戳的时候，这个Bill实际上已经不是一个 Bill 了
     */
    private int viewType;

    /**
     * 时间戳内的支出合计
     */
    private float out;

    /**
     * 时间戳内的收入合计
     */
    private float in;

    /**
     * 两种 ViewType
     */
    private static final int TYPE_BILL=0;
    private static final int TYPE_TIME=1;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
