package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/2/9.
 */
public class ImageInfo{
    private String name;
    private String path;
    private boolean checked;

    public ImageInfo(String name,String path){
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

