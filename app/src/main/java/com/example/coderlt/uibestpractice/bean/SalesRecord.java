package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/2/7.
 */

public class SalesRecord {
    //  商品
    private Goods goods;
    //  商品售出的件数
    private int    count;

    public SalesRecord(Goods goods,int count){
        this.goods = goods;
        this.count = count;
    }

    public SalesRecord(){}
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
