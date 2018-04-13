package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/4/12.
 */

public class ShopInfo {
    String shopName;
    String shopId;

    public ShopInfo(String shopName, String shopId) {
        this.shopName = shopName;
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "shopName='" + shopName + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
