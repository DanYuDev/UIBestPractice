package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/2/7.
 */

public class Goods {
    private String name;
    private float  price;

    public Goods(){}

    public Goods(String name,float price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
