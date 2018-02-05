package com.example.coderlt.uibestpractice.bean;

import java.io.Serializable;

/**
 * Created by coderlt on 2018/1/10.
 */

public class Option implements Serializable{
    private String name;
    private String imgUrl;
    private String config_id;
    private String url; //如果此按钮对应的是web页面，则提供一个此 url

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getConfig_id() {
        return config_id;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", config_id='" + config_id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
